package com.holland.holland.filter;

import com.alibaba.fastjson.JSONObject;
import com.holland.holland.common.RedisController;
import com.holland.holland.util.DateUtil;
import com.holland.holland.util.RequestUtil;
import com.holland.holland.util.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;


@Order(2)
@Component
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    private final List<String> routeWhitelist = List.of(
            "/user/customer/login",
            "/user/customer/add",
            "/user/employee/login",
            "/user/employee/add"
    );

    @Resource
    private RedisController redisController;

    @Value("${spring.redis.token-timeout:60}")
    private long tokenTimeout;

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    private long TOKEN_TIMEOUT_OF_MILLI;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        TOKEN_TIMEOUT_OF_MILLI = tokenTimeout * 60 * 1000;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestURI = request.getRequestURI();
        final String referer = request.getHeader("Referer");

        /* BEGIN 放行swagger   */
        if (referer != null && (referer.equals("http://localhost:10011/swagger-ui.html") || referer.equals("http://127.0.0.1:10011/swagger-ui.html") || referer.equals("http://119.23.68.6:10011/swagger-ui.html"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (requestURI.startsWith("/webjars/springfox-swagger-ui") || requestURI.startsWith("/swagger-")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        /* END 放行swagger   */

        /* 精确的路径匹配模式 */
        final boolean notNeedToken = routeWhitelist.stream()
                .anyMatch(it -> it.equals(requestURI));
        //token验证
        if (!notNeedToken) {
            final String token = RequestUtil.getToken(request);
            //token验证: 需要token的接口没传token
            if (token == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            //token验证: token有效性
            final JSONObject auth = redisController.getFromToken(token);
            final String decodeToken = new String(Base64.getDecoder().decode(token));
            final int length = decodeToken.length();
            final LocalDateTime createTime = DateUtil.getDate(decodeToken.substring(length - 16, length - 2));
            if (auth == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            } else if (System.currentTimeMillis() - createTime.toEpochSecond(ZoneOffset.of("+8")) * 1000 >= TOKEN_TIMEOUT_OF_MILLI) {
                response.setStatus(HttpStatus.OK.value());
                final Response error = Response.error("会话过期，请重新登录");
                servletResponse.getOutputStream().write(error.toJsonString().getBytes(StandardCharsets.UTF_8));
                return;
            } else {
                final String from = decodeToken.substring(length - 2);

                //把常用的username装在attribute里面
                request.setAttribute("userStr", auth.toString());
                request.setAttribute("loginName", auth.get("user"));
                request.setAttribute("userId", auth.get("id"));
                request.setAttribute("from", from);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
