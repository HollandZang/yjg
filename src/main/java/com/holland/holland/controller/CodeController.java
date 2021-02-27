package com.holland.holland.controller;

import com.holland.holland.pojo.Code;
import com.holland.holland.service.ICodeService;
import com.holland.holland.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
