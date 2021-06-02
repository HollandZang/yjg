package com.holland.holland.controller;

import com.holland.holland.aop.AuthCheck;
import com.holland.holland.aop.LogForLogin;
import com.holland.holland.common.CommonCache;
import com.holland.holland.common.RedisController;
import com.holland.holland.pojo.User;
import com.holland.holland.service.IUserService;
import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import com.holland.holland.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"员工账号类API"})
@RestController
@RequestMapping("user/employee")
public class EmployeeController {

    @Resource
    private IUserService userService;

    @Resource
    private RedisController redisController;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    @LogForLogin(from = "01接单系统后台")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", defaultValue = "17781671532", required = true),
            @ApiImplicitParam(name = "pwd", defaultValue = "admin", required = true),})
    @PostMapping("login")
    public Response login(String user, String pwd) throws Exception {
        ValidateUtil.notEmpty(user, "账号");
        ValidateUtil.maxLength(user, 16, "账号");
        ValidateUtil.notEmpty(pwd, "密码");

        final User dbUser = userService.getModelByUser(user);
        if (dbUser == null) {
            return Response.info(ResultCodeEnum.NotFound, user);
        }
        if (encoder.matches(pwd, dbUser.getPwd())) {
            dbUser.setPwd(null);
            final String token = redisController.setToken(user, dbUser, "01");
            dbUser.setToken(token);
        } else {
            return Response.info(ResultCodeEnum.ErrorPwd, user);
        }

        /*User login = userService.login(user, pwd);
        if (login == null) {
            User isExist = userService.getModelByUser(user);
            if (isExist == null) {
                return Response.info(ResultCodeEnum.NotFound, user);
            } else {
                return Response.info(ResultCodeEnum.ErrorPwd, user);
            }
        }*/
//        String[] roleArr = Arrays.stream(login.getRole().split(","))
//                .map(s -> CommonCache.ROLE.get(s)).toArray(String[]::new);
        String s1 = Arrays.stream(dbUser.getRole().split(","))
                .findFirst()
                .get();
        dbUser.setRole(CommonCache.ROLE.get(s1)).setLoginTime(LocalDateTime.now());
        return Response.success(dbUser);
    }

    @ApiOperation("用户新增")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "user", value = "登录账号", required = true),
            @ApiImplicitParam(name = "pwd", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称"),
            @ApiImplicitParam(name = "role", value = "角色，只能有一个", defaultValue = "接单员", required = true, dataTypeClass = String.class),})
    @PostMapping("add")
    @AuthCheck(value = AuthCheck.AuthRole.ADMIN)
    public Response add(String user, String pwd, String name, String role) throws Exception {
        ValidateUtil.notEmpty(user, "账号");
        ValidateUtil.maxLength(user, 16, "账号");
        ValidateUtil.notEmpty(pwd, "密码");
        ValidateUtil.maxLength(name, 20, "昵称");

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
                .setPwd(encoder.encode(pwd))
                .setRole(first.get().getKey())
                .setName(name != null ? name : "默认用户")
                .setcTime(new Date())
        );
        return Response.success();
    }

//    @LogForLoginout
//    @ApiOperation("退出登录")
//    @ApiImplicitParam(name = "user", required = true)
//    @PostMapping("logout")
//    public Response logout(String user) {
//        final User modelByUser = userService.getModelByUser(user);
//        CommonCache.USER_MAP.remove(modelByUser.getId());
//        return Response.success();
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
    })
    @ApiOperation("获取所有用户")
    @GetMapping("list")
    @AuthCheck(AuthCheck.AuthRole.ADMIN)
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
