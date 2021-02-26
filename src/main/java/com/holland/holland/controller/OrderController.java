package com.holland.holland.controller;

import com.holland.holland.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"单子类API"})
@RestController
@RequestMapping("order")
public class OrderController {

    @ApiOperation("上传单子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page"),
            @ApiImplicitParam(name = "limit")})
    @PostMapping("upload")
    public Response uploadOrder(String page) {
        return Response.success(page);
    }
}
