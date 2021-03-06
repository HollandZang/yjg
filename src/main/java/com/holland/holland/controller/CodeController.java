package com.holland.holland.controller;

import com.holland.holland.aop.AuthCheck;
import com.holland.holland.common.CommonCache;
import com.holland.holland.pojo.Code;
import com.holland.holland.service.ICodeService;
import com.holland.holland.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = {"码表类API"})
@RestController
@RequestMapping("code")
public class CodeController {

    @Resource
    private ICodeService codeService;

    @Resource
    private CommonCache commonCache;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
    })
    @ApiOperation("刷新系统参数")
    @GetMapping("refresh")
    @AuthCheck(AuthCheck.AuthRole.ADMIN)
    public Response refresh() throws Exception {
        commonCache.init();
        return Response.success();
    }

    @ApiOperation("获取码表所有类别")
    @GetMapping("types")
    public Response types() {
        List<Map> types = codeService.getTypes();
        return Response.success(types);
    }

    @ApiOperation("获取码表某个类别的条目")
    @ApiImplicitParam(name = "type", value = "码表值")
    @GetMapping("codes")
    public Response codes(String type) throws Exception {
        List<Code> codes = codeService.get(type);
        return Response.success(codes);
    }
}
