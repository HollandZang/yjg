package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.aop.AuthCheck;
import com.holland.holland.aop.LogForLogin;
import com.holland.holland.common.RedisController;
import com.holland.holland.pojo.Customer;
import com.holland.holland.service.ICustomerService;
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
import java.util.Date;
import java.util.Map;

@Api(tags = {"顾客账号类API"})
@RestController
@RequestMapping("user/customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    @Resource
    private RedisController redisController;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    @LogForLogin(from = "02接单系统前台")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", defaultValue = "tester", required = true),
            @ApiImplicitParam(name = "pwd", defaultValue = "admin", required = true),})
    @PostMapping("login")
    public Response login(String user, String pwd) throws Exception {
        ValidateUtil.notEmpty(user, "账号");
        ValidateUtil.maxLength(user, 16, "账号");
        ValidateUtil.notEmpty(pwd, "密码");

        final Customer dbUser = customerService.getModelByLoginName(user);
        if (dbUser == null) {
            return Response.info(ResultCodeEnum.NotFound, user);
        }
        if (encoder.matches(pwd, dbUser.getPwd())) {
            dbUser.setPwd(null);
            final String token = redisController.setToken(user, dbUser, "02");
            return Response.success(dbUser.setToken(token));
        } else {
            return Response.info(ResultCodeEnum.ErrorPwd, user);
        }
    }

    @ApiOperation("用户新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "登录账号", required = true),
            @ApiImplicitParam(name = "pwd", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称"),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true),})
    @PostMapping("add")
    public Response add(String user, String pwd, String name, String phone) throws Exception {
        ValidateUtil.notEmpty(user, "账号");
        ValidateUtil.maxLength(user, 16, "账号");
        ValidateUtil.notEmpty(pwd, "密码");
        ValidateUtil.maxLength(name, 20, "昵称");
        ValidateUtil.notEmpty(phone, "联系方式");
        ValidateUtil.maxLength(phone, 20, "联系方式");

        /*用户名不能重复*/
        final Customer model = customerService.getModelByLoginName(user);
        if (model != null) {
            return Response.info(ResultCodeEnum.DuplicateUser, user);
        }

        final Date now = new Date();
        customerService.add(new Customer()
                .setUser(user)
                .setPwd(encoder.encode(pwd))
                .setName(name != null ? name : "默认用户")
                .setPhone(phone)
                .setCreateTime(now)
                .setUpdateTime(now)
        );
        return Response.success();
    }

//    @LogForLoginout
//    @ApiOperation("退出登录")
//    @ApiImplicitParam(name = "user", required = true)
//    @PostMapping("logout")
//    public Response logout(String user) {
//        final User modelByUser = customerService.getModelByLoginName(user);
//        CommonCache.USER_MAP.remove(modelByUser.getId());
//        return Response.success();
//    }

    @ApiOperation("获取所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),})
    @GetMapping("list")
    @AuthCheck(AuthCheck.AuthRole.ADMIN)
    public Response list(String page, String limit) throws Exception {
        PageInfo list = customerService.getList(Map.of("page", page, "limit", limit));
        return Response.success(list.getList(), list.getTotal());
    }
}
