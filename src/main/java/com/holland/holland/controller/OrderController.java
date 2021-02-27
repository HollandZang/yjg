package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.pojo.Order;
import com.holland.holland.pojo.User;
import com.holland.holland.service.IOrderService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"单子类API"})
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @Resource
    private IUserService userService;

    @ApiOperation("新增单子")
    @PostMapping("add")
    public Response add(Order order) throws Exception {
        // TODO: 2021/2/28 新增单子的权限控制
        orderService.add(order.setStatus(1));
        return Response.success(order);
    }

    @ApiOperation("获取单子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),
//            @ApiImplicitParam(name = "sTime", value = "开始时间：yyyy-MM-dd"),
//            @ApiImplicitParam(name = "eTime", value = "结束时间：yyyy-MM-dd"),
            @ApiImplicitParam(name = "user", value = "只看user的接的单子", dataType = "Integer"),})
    @GetMapping("list")
    public Response list(String page, String limit, Integer user) throws Exception {
        Map map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("user", user);
        PageInfo list = orderService.getList(map);
        return Response.success(list.getList(), list.getTotal());
    }

    @ApiOperation("获取单子详情")
    @ApiImplicitParam(name = "id", value = "单子id")
    @GetMapping("info")
    public Response info(Integer id) throws Exception {
        Order model = orderService.getModel(id);
        return Response.success(model);
    }

    @ApiOperation("接单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "接单人id", dataType = "Integer"),})
    @PostMapping("claim")
    public Response claim(Integer id, Integer user) throws Exception {
        User model = userService.getModel(user);
        /*有无接单权限*/
        if (!model.getRole().contains("2")) {
            return Response.info(ResultCodeEnum.UnAuth, "");
        }

        orderService.update(new Order()
                .setId(id)
                .setClaimUserId(user)
                .setClaimTime(new Date())
        );

        return Response.success();
    }

    @ApiOperation("接单人提出单子已完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "接单人id", dataType = "Integer"),})
    @PostMapping("finish")
    public Response finish(Integer id, Integer user) throws Exception {
        Order model = orderService.getModel(id);
        if (model == null) {
            return Response.info(ResultCodeEnum.OrderNotExist, "");
        }
        if (model.getClaimUserId() == null) {
            return Response.info(ResultCodeEnum.NotClaimOrder, "");
        }
        if (!model.getClaimUserId().equals(user)) {
            return Response.info(ResultCodeEnum.NotSelfOrder, "");
        }

        orderService.update(new Order()
                .setId(id)
                .setETime(new Date())
        );
        return Response.success();
    }

    @ApiOperation("审核单子确实完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "审核人id", dataType = "Integer"),})
    @PostMapping("verify")
    public Response verify(Integer id, Integer user) throws Exception {
        User model = userService.getModel(user);
        /*有无审核单子的权限*/
        if (!model.getRole().contains("3")) {
            return Response.info(ResultCodeEnum.UnAuth, "");
        }

        orderService.update(new Order()
                .setId(id)
                .setVerifyUserId(user)
                .setVerityTime(new Date())
        );
        return Response.success();
    }
}
