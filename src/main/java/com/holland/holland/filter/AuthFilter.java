package com.holland.holland.filter;

import com.alibaba.fastjson.JSONObject;
import com.holland.holland.common.RedisController;
import com.holland.holland.util.RequestUtil;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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
            if (auth == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            } else {
                //把常用的username装在attribute里面
                request.setAttribute("user", auth.get("user"));
                request.setAttribute("userId", auth.get("id"));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
