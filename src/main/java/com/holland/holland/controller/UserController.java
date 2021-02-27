package com.holland.holland.controller;

import com.holland.holland.common.CommonCache;
import com.holland.holland.log.LogForLogin;
import com.holland.holland.log.LogForLoginout;
import com.holland.holland.pojo.User;
import com.holland.holland.service.IUserService;
import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Api(tags = {"用户类API"})
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @LogForLogin
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "pwd", defaultValue = "admin", required = true),})
    @PostMapping("login")
    public Response login(String user, String pwd) {
        User login = userService.login(user, pwd);
        if (login == null) {
            User isExist = userService.getModelByUser(user);
            if (isExist == null) {
                return Response.info(ResultCodeEnum.NotFound, user);
            } else {
                return Response.info(ResultCodeEnum.ErrorPwd, user);
            }
        }
        String[] roleArr = Arrays.stream(login.getRole().split(","))
                .map(s -> CommonCache.ROLE.get(s)).toArray(String[]::new);
        return Response.success(login.setRoleArr(roleArr));
    }

    @ApiOperation("用户新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "登录账号", required = true),
            @ApiImplicitParam(name = "pwd", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称"),
            @ApiImplicitParam(name = "roles", value = "角色组", defaultValue = "1,2,3", required = true, dataTypeClass = Integer[].class),})
    @PostMapping("add")
    public Response add(String user, String pwd, String name, Integer[] roles) throws Exception {
        userService.add(new User()
                .setUser(user)
                .setPwd(pwd)
                .setRole(Arrays.stream(roles).map(Object::toString).collect(Collectors.joining(",")))
                .setName(name != null ? name : "默认用户")
                .setCTime(new Date())
        );
        return Response.success();
    }

    @LogForLoginout
    @ApiOperation("退出登录")
    @ApiImplicitParam(name = "user", required = true)
    @PostMapping("logout")
    public Response logout(String user) {
        return Response.success();
    }
}
