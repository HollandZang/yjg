package com.holland.holland.controller;

import com.alibaba.fastjson.JSONObject;
import com.holland.holland.log.LogForLogin;
import com.holland.holland.log.LogForLoginout;
import com.holland.holland.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"用户类API"})
@RestController
@RequestMapping("user")
public class UserController {

    @LogForLogin
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user"),
            @ApiImplicitParam(name = "pwd"),})
    @PostMapping("login")
    public Response login(@RequestBody JSONObject object) {
        return Response.success();
    }

    @ApiOperation("用户新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user"),
            @ApiImplicitParam(name = "pwd"),
            @ApiImplicitParam(name = "role"),})
    @PostMapping("add")
    public Response add(@RequestBody JSONObject object) {
        return Response.success();
    }

    @LogForLoginout
    @ApiOperation("退出登录")
    @ApiImplicitParam(name = "user")
    @PostMapping("logout")
    public Response logout(@RequestBody JSONObject object) {
        return Response.success();
    }
}
