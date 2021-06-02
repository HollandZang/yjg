package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.aop.LogIgnore;
import com.holland.holland.pojo.User;
import com.holland.holland.service.ILogService;
import com.holland.holland.util.RequestUtil;
import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"系统日志类API"})
@RequestMapping("log")
@RestController
public class LogController {

    @Resource
    private ILogService logService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation("获取日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),})
    @LogIgnore
    @GetMapping("list")
    public Response list(String page, String limit) throws Exception {
        final boolean isBackUser = RequestUtil.isBackUser(request);
        if (isBackUser) {
            final User user = RequestUtil.getEmployee(request);
            if ("管理员".equals(user.getRole())) {
                final Map map = new HashMap<>(4);
                map.put("page", page);
                map.put("limit", limit);
                final PageInfo list = logService.getList(map);
                return Response.success(list.getList(), list.getTotal());
            }
        }
        return Response.info(ResultCodeEnum.UnAuth, "");
    }

    @ApiOperation("获取登录日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),})
    @LogIgnore
    @GetMapping("list/login")
    public Response loginList(String page, String limit) throws Exception {
        final boolean isBackUser = RequestUtil.isBackUser(request);
        if (isBackUser) {
            final User user = RequestUtil.getEmployee(request);
            if ("管理员".equals(user.getRole())) {
                final PageInfo list = logService.getListLogin(Map.of("page", page, "limit", limit));
                return Response.success(list.getList(), list.getTotal());
            }
        }
        return Response.info(ResultCodeEnum.UnAuth, "");
    }
}
