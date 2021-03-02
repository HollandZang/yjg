package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.log.LogIgnore;
import com.holland.holland.service.ILogService;
import com.holland.holland.util.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("log")
@RestController
public class LogController {

    @Resource
    private ILogService logService;

    @ApiOperation("获取日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),})
    @LogIgnore
    @GetMapping("list")
    public Response list(String page, String limit) throws Exception {
        final Map map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        final PageInfo list = logService.getList(map);
        return Response.success(list.getList(), list.getTotal());
    }
}
