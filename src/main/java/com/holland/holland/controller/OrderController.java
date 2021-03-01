package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.common.CommonCache;
import com.holland.holland.pojo.Order;
import com.holland.holland.vo.OrderUpdate;
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
        orderService.add(order
                .setcTime(new Date())
                .setStatus1("有效")
                .setStatus2("未接单")
                .setStatus3("未完成")
        );
        return Response.success();
    }

    @ApiOperation("获取单子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),
//            @ApiImplicitParam(name = "cTime", value = "单子上传时间时间：yyyy-MM-dd"),
//            @ApiImplicitParam(name = "claimTime", value = "接单时间：yyyy-MM-dd"),
//            @ApiImplicitParam(name = "eTime", value = "完成时间：yyyy-MM-dd"),
            @ApiImplicitParam(name = "cUserId", value = "创建单子人的id", dataType = "Integer"),
            @ApiImplicitParam(name = "claimUserId", value = "接单人的id", dataType = "Integer"),
            @ApiImplicitParam(name = "status1", value = "状态1：有效&无效"),
            @ApiImplicitParam(name = "status2", value = "状态2：已做单&未做单"),
            @ApiImplicitParam(name = "status3", value = "状态3：已完成&未完成"),})
    @GetMapping("list")
    public Response list(String page, String limit,
                         String cUserId, String claimUserId,
                         String status1, String status2, String status3) throws Exception {
        Map map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("cUserId", cUserId);
        map.put("claimUserId", claimUserId);
        map.put("status1", status1);
        map.put("status2", status2);
        map.put("status3", status3);
        PageInfo list = orderService.getList(map);
        return Response.success(list.getList(), list.getTotal());
    }

    @ApiOperation("修改单子")
	@GetMapping("update")
	public Response update(OrderUpdate order) throws Exception {
		orderService.update(order);
		return Response.success();
	}

//    @ApiOperation("获取单子详情")
//    @ApiImplicitParam(name = "id", value = "单子id")
//    @GetMapping("info")
//    public Response info(Integer id) throws Exception {
//        Order model = orderService.getModel(id);
//        return Response.success(model);
//    }

    @ApiOperation("做单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "做单人id", dataType = "Integer"),})
    @PostMapping("claim")
    public Response claim(Integer id, Integer user) throws Exception {
//        User model = userService.getModel(user);
        /*有无接单权限*/
//        if (!model.getRole().contains("1") || !model.getRole().contains("2")) {
//            return Response.info(ResultCodeEnum.UnAuth, "");
//        }

        Order model = orderService.getModel(id);
		if ("无效".equals(model.getStatus1())){
			return Response.info(ResultCodeEnum.OrderInvalid, "");
		}
		if (null!=model.getClaimUserId()){
			return Response.info(ResultCodeEnum.IsClaimOrder, "");
		}

        orderService.update(new Order()
                .setId(id)
                .setClaimUserId(user)
                .setClaimTime(new Date())
                .setStatus2(CommonCache.ORDER_STATUS_2.get("1"))
        );

        return Response.success();
    }

//    @ApiOperation("接单人提出单子已完成")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
//            @ApiImplicitParam(name = "user", value = "接单人id", dataType = "Integer"),})
//    @PostMapping("finish")
//    public Response finish(Integer id, Integer user) throws Exception {
//        Order model = orderService.getModel(id);
//        if (model == null) {
//            return Response.info(ResultCodeEnum.OrderNotExist, "");
//        }
//        if (model.getClaimUserId() == null) {
//            return Response.info(ResultCodeEnum.NotClaimOrder, "");
//        }
//        if (!model.getClaimUserId().equals(user)) {
//            return Response.info(ResultCodeEnum.NotSelfOrder, "");
//        }
//
//        orderService.update(new Order()
//                .setId(id)
//                .seteTime(new Date())
//                .setStatus3(CommonCache.ORDER_STATUS_3.get("1"))
//        );
//        return Response.success();
//    }
//
//    @ApiOperation("使单子无效")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
//            @ApiImplicitParam(name = "user", value = "操作的人，用来判断权限", dataType = "Integer"),})
//    @PostMapping("invalid")
//    public Response invalid(Integer id, Integer user) throws Exception {
//        User model = userService.getModel(user);
//        /*有无无效单子的权限*/
//        if (!model.getRole().contains("1") || !model.getRole().contains("2")) {
//            return Response.info(ResultCodeEnum.UnAuth, "");
//        }
//
//        orderService.update(new Order()
//                .setId(id)
//                .setStatus1(CommonCache.ORDER_STATUS_1.get("2"))
//        );
//        return Response.success();
//    }

//    @ApiOperation("审核单子确实完成")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
//            @ApiImplicitParam(name = "user", value = "审核人id", dataType = "Integer"),})
//    @PostMapping("verify")
//    public Response verify(Integer id, Integer user) throws Exception {
//        User model = userService.getModel(user);
//        /*有无审核单子的权限*/
//        if (!model.getRole().contains("3")) {
//            return Response.info(ResultCodeEnum.UnAuth, "");
//        }
//
//        orderService.update(new Order()
//                .setId(id)
//                .setVerifyUserId(user)
//                .setVerityTime(new Date())
//        );
//        return Response.success();
//    }
}
