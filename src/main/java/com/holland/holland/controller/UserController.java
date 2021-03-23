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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"用户类API"})
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "pwd", defaultValue = "admin", required = true),})
    @PostMapping("login")
    @LogForLogin(description = "测试文本1:[%s], 测试文本2:[%s]", params = {"user", "pwd"})
    public Response login(String user, String pwd) throws Exception {
        User login = userService.login(user, pwd);
        if (login == null) {
            User isExist = userService.getModelByUser(user);
            if (isExist == null) {
                return Response.info(ResultCodeEnum.NotFound, user);
            } else {
                return Response.info(ResultCodeEnum.ErrorPwd, user);
            }
        }
//        String[] roleArr = Arrays.stream(login.getRole().split(","))
//                .map(s -> CommonCache.ROLE.get(s)).toArray(String[]::new);
        String s1 = Arrays.stream(login.getRole().split(","))
                .findFirst()
                .get();
        return Response.success(login.setRole(CommonCache.ROLE.get(s1)));
    }

    @ApiOperation("用户新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "登录账号", required = true),
            @ApiImplicitParam(name = "pwd", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称"),
            @ApiImplicitParam(name = "role", value = "角色，只能有一个", defaultValue = "接单员", required = true, dataTypeClass = String.class),})
    @PostMapping("add")
    public Response add(String user, String pwd, String name, String role) throws Exception {
        /*用户名不能重复*/
        User model = userService.getModelByUser(user);
        if (model != null) {
            return Response.info(ResultCodeEnum.DuplicateUser, user);
        }
        /*权限组必须存在*/
        Optional<Map.Entry<String, String>> first =
                CommonCache.ROLE.entrySet().stream().filter(entry -> entry.getValue().equals(role)).findFirst();
        if (first.isEmpty()) {
            return Response.info(ResultCodeEnum.NoSuchRole, role);
        }

        userService.add(new User()
                .setUser(user)
                .setPwd(pwd)
                .setRole(first.get().getKey())
                .setName(name != null ? name : "默认用户")
                .setcTime(new Date())
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

    @ApiOperation("获取所有用户")
    @GetMapping("list")
    public Response list() {
        List<User> all = userService.getAll();
        List<User> collect = all.stream().peek(user -> {
            String s1 = Arrays.stream(user.getRole().split(","))
                    .findFirst()
                    .get();
            user.setRole(CommonCache.ROLE.get(s1));
        }).collect(Collectors.toList());
        return Response.success(collect);
    }
}
