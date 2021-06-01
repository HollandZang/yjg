package com.holland.holland.aop;

import com.alibaba.fastjson.JSON;
import com.holland.holland.pojo.Log;
import com.holland.holland.pojo.LogLogin;
import com.holland.holland.pojo.User;
import com.holland.holland.service.ILogService;
import com.holland.holland.util.IPUtils;
import com.holland.holland.util.RequestUtil;
import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;

@Aspect
@Component
public class AOP {

    @Resource
    private ILogService logService;

    @Resource
    private HttpServletRequest request;

    @Pointcut(value = "((@within(org.springframework.web.bind.annotation.RestController))"
            + "&& !(@annotation(com.holland.holland.aop.LogIgnore))"
            + "&& !(@annotation(com.holland.holland.aop.LogForLogin))"
            + "&& !(@annotation(com.holland.holland.aop.LogForLoginout))"
            + ")")
    private void pointCut() {
    }

    @Pointcut(value = "@annotation(com.holland.holland.aop.LogForLogin)")
    private void pointCutForLogin() {
    }

    @Pointcut(value = "@annotation(com.holland.holland.aop.LogForLoginout)")
    private void pointCutForLoginout() {
    }

    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        /*权限校验*/
        AuthCheck authCheck = method.getAnnotation(AuthCheck.class);
        if (authCheck != null) {
            final boolean isBackUser = RequestUtil.isBackUser(request);
            final AuthCheck.AuthRole role;
            if (isBackUser) {
                final User user = RequestUtil.getEmployee(request);
                if ("管理员".equals(user.getRole())) {
                    role = AuthCheck.AuthRole.ADMIN;
                } else {
                    role = AuthCheck.AuthRole.EMPLOYEE;
                }
            } else {
                role = AuthCheck.AuthRole.CUSTOMER;
            }

            if (!List.of((authCheck.value())).contains(role)) {
                return Response.info(ResultCodeEnum.UnAuth, "");
            }
        }

        //GET, FORM 请求的参数     /*序列化时过滤掉request和response*/     /*过滤掉文件*/
        final Object[] args = joinPoint.getArgs();
        final String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        final String param = params.apply(parameterNames, args);
        //PAYLOAD 请求的参数 暂时没用到post json

        final String content;
        LogContent logContent = method.getAnnotation(LogContent.class);
        if (logContent != null) {
            final Map<String, Object> paramMap = this.paramMap.apply(parameterNames, args);
            String description = logContent.description();
            String[] params = logContent.params();
            Object[] objects = Arrays.stream(params).map(paramMap::get).toArray(Object[]::new);
            content = new Formatter().format(description, objects).toString();
        } else content = null;

        final Object proceed = joinPoint.proceed();

        final Response response = (Response) proceed;
        final String res = JSON.toJSONString(response);
        final Log log = new Log()
                .setIp(IPUtils.getIpAddr(request))
                .setOperateApi(content != null ? content : request.getRequestURI())
                .setOperateTime(new Date())
                .setOperateType("")//从annotation里面定制化
                .setOperateUser(RequestUtil.getLoginName(request))
                .setParam(truncByte(param, 1024))
                .setResult(response.getCode())
                .setResponse(truncByte(res, 1024));

        logService.add(log);
        return proceed;
    }

//    @AfterReturning(value = "pointCut()", returning = "result")
//    public void doAfter(JoinPoint joinPoint, Object result) throws Exception {
//        Response response = (Response) result;
//
//        //GET, FORM 请求的参数     /*序列化时过滤掉request和response*/     /*过滤掉文件*/
//        final Object[] args = joinPoint.getArgs();
//        final String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
//        final String param = params.apply(parameterNames, args);
//        final String res = JSON.toJSONString(response);
//        //PAYLOAD 请求的参数 暂时没用到post json
//
//        final String content;
//        LogContent annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogContent.class);
//        if (annotation != null) {
//            final Map<String, Object> paramMap = this.paramMap.apply(parameterNames, args);
//            String description = annotation.description();
//            String[] params = annotation.params();
//            Object[] objects = Arrays.stream(params).map(paramMap::get).toArray(Object[]::new);
//            content = new Formatter().format(description, objects).toString();
//        } else content = null;
//
//        final Log log = new Log()
//                .setIp(IPUtils.getIpAddr(request))
//                .setOperateApi(content != null ? content : request.getRequestURI())
//                .setOperateTime(new Date())
//                .setOperateType("")//从annotation里面定制化
//                .setOperateUser(RequestUtil.getUser(request))
//                .setParam(param.length() < 1024 ? param : param.substring(0, 1024))
//                .setResult(response.getCode())
//                .setResponse(res.length() < 1024 ? res : res.substring(0, 1024));
//
//        logService.add(log);
//    }
//
//    @AfterThrowing(value = "pointCut()", throwing = "e")
//    public void doOnException(JoinPoint joinPoint, Exception e) throws Exception {
//        //请求的参数     /*序列化时过滤掉request和response*/     /*过滤掉文件*/
//        final Object[] args = joinPoint.getArgs();
//        final String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
//        final String param = params.apply(parameterNames, args);
//
//        final String res = e.getClass().getName() + ":\t" + e.getMessage() + '\n' + Arrays.stream(e.getStackTrace())
//                .map(StackTraceElement::toString)
//                .reduce((s, s2) -> s + '\n' + s2);
//
//        final Log log = new Log()
//                .setIp(IPUtils.getIpAddr(request))
//                .setOperateApi(request.getRequestURI())
//                .setOperateTime(new Date())
//                .setOperateType("")//从annotation里面定制化
//                .setOperateUser(RequestUtil.getUser(request))
//                .setParam(param.length() < 1024 ? param : param.substring(0, 1024))
//                .setResult(ResultCodeEnum.ServiceException.getCode())
//                .setResponse(res.length() < 1024 ? res : res.substring(0, 1024));
//
//        logService.add(log);
//    }

    @AfterReturning(value = "pointCutForLogin()", returning = "result")
    public void loginAfter(JoinPoint joinPoint, Object result) throws Exception {
        final Response response = (Response) result;
        final LogForLogin annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogForLogin.class);
        logService.addLogin(new LogLogin()
                .setOperateUser(joinPoint.getArgs()[0].toString())
                .setOperateTime(new Date())
                .setOperateType("1")
                .setFrom(annotation.from())
                .setIp(IPUtils.getIpAddr(request))
                .setResult(response.getCode())
                .setResponse(JSON.toJSONString(response)));
    }

    private String truncByte(String field, int length) {
        if (field == null) {
            return null;
        }
        int len = 0;
        char[] charArray = field.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            len += c > 127 || c == 97 ? 2 : 1;
            if (len == length - 3 || len == length - 4) return field.substring(0, i) + "...";
        }
        return field;
    }

    private final BiFunction<String[], Object[], Map<String, Object>> paramMap = (names, values) -> {
        final int length = names.length;
        final Map<String, Object> map = new HashMap<>();
        if (length == 0) {
            return map;
        }
        for (int i = 0; i < length; i++) {
            if (values[i] instanceof HttpServletRequest || values[i] instanceof HttpServletResponse
                    || values[i] instanceof MultipartFile || values[i] instanceof File) {
                continue;
            }

            map.put(names[i], values[i]);
        }
        return map;
    };

    private final BiFunction<String[], Object[], String> params = (names, values) -> {
        int length = names.length;
        if (length == 0) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < length; i++) {
            if (values[i] instanceof HttpServletRequest || values[i] instanceof HttpServletResponse
                    || values[i] instanceof MultipartFile || values[i] instanceof File) {
                continue;
            }

            String val;
            try {
                val = JSON.toJSONString(values[i]);
            } catch (Exception e) {
                val = "\"" + values[i] + "\"";
            }
            builder.append("\"").append(names[i]).append("\":").append(val).append(",");
        }
        int len = builder.length();
        return builder.replace(len - 1, len, "}").toString();
    };
}
