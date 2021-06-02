package com.holland.holland.controller;

import com.github.pagehelper.PageInfo;
import com.holland.holland.aop.AuthCheck;
import com.holland.holland.common.CommonCache;
import com.holland.holland.pojo.Order;
import com.holland.holland.service.IOrderService;
import com.holland.holland.util.RequestUtil;
import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import com.holland.holland.vo.OrderUpdateAllPermission;
import com.holland.holland.vo.OrderUpdateCustomer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"单子类API"})
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private IOrderService orderService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMg=="),
    })
    @ApiOperation("新增单子")
    @PostMapping("add")
    @AuthCheck(value = AuthCheck.AuthRole.CUSTOMER)
    public Response add(Order order) throws Exception {
        orderService.add(order
                .setcUserId(RequestUtil.getUserId(request))
                .setcTime(new Date())
                .setStatus1("有效")
                .setStatus2("未接单")
                .setStatus3("未完成")
        );
        return Response.success();
    }

    @ApiOperation("获取单子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "page", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", defaultValue = "10"),
            @ApiImplicitParam(name = "cTime", value = "单子上传时间时间：yyyy-MM-dd"),
//            @ApiImplicitParam(name = "claimTime", value = "接单时间：yyyy-MM-dd"),
//            @ApiImplicitParam(name = "eTime", value = "完成时间：yyyy-MM-dd"),
            @ApiImplicitParam(name = "cUserId", value = "创建单子人的id", dataType = "Integer"),
            @ApiImplicitParam(name = "claimUserId", value = "接单人的id", dataType = "Integer"),
            @ApiImplicitParam(name = "status1", value = "状态1：有效&无效"),
            @ApiImplicitParam(name = "status2", value = "状态2：已做单&未做单"),
            @ApiImplicitParam(name = "status3", value = "状态3：已完成&未完成"),
            @ApiImplicitParam(name = "natureOrder", value = "是否是自然排序true（升序），默认false（降序）", dataTypeClass = Boolean.class),
    })
    @GetMapping("list")
    public Response list(String page, String limit,
                         Integer cUserId, Integer claimUserId,
                         String status1, String status2, String status3,
                         String cTime/*, String claimTime, String eTime*/,
                         Boolean natureOrder) throws Exception {
        /*顾客只能访问自己的单子*/
        if (!RequestUtil.isBackUser(request)) {
            cUserId = RequestUtil.getUserId(request);
        }

        Map map = new HashMap<>(16);
        map.put("page", page);
        map.put("limit", limit);
        map.put("cUserId", cUserId);
        map.put("claimUserId", claimUserId);
        map.put("status1", status1);
        map.put("status2", status2);
        map.put("status3", status3);
        map.put("cTime", cTime);
        map.put("natureOrder", natureOrder);
//        map.put("claimTime", claimTime);
//        map.put("eTime", eTime);
        PageInfo list = orderService.getList(map);
        return Response.success(list.getList(), list.getTotal());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
    })
    @ApiOperation("管理员修改单子")
    @GetMapping("update/admin")
    @AuthCheck(value = AuthCheck.AuthRole.ADMIN)
    public Response updateByAdmin(OrderUpdateAllPermission order) throws Exception {
        orderService.update(order.convert());
        return Response.success();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMg=="),
    })
    @ApiOperation("顾客修改单子")
    @GetMapping("update")
    @AuthCheck(value = AuthCheck.AuthRole.CUSTOMER)
    public Response updateByCustomer(OrderUpdateCustomer order) throws Exception {
        orderService.update(order.convert());
        return Response.success();
    }

//    @ApiOperation("获取单子详情")
//    @ApiImplicitParam(name = "id", value = "单子id")
//    @GetMapping("info")
//    public Response info(Integer id) throws Exception {
//        Order model = orderService.getModel(id);
//        return Response.success(model);
//    }

    @ApiOperation("员工接单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
    })
    @PostMapping("claim")
    @AuthCheck(value = {AuthCheck.AuthRole.EMPLOYEE, AuthCheck.AuthRole.ADMIN})
    public Response claim(Integer id) throws Exception {
//        User model = userService.getModel(user);
        /*有无接单权限*/
//        if (!model.getRole().contains("1") || !model.getRole().contains("2")) {
//            return Response.info(ResultCodeEnum.UnAuth, "");
//        }

        Order model = orderService.getModel(id);
        if ("无效".equals(model.getStatus1())) {
            return Response.info(ResultCodeEnum.OrderInvalid, "");
        }
        if (null != model.getClaimUserId()) {
            return Response.info(ResultCodeEnum.IsClaimOrder, "");
        }

        orderService.update(new Order()
                .setId(id)
                .setClaimUserId(RequestUtil.getUserId(request))
                .setClaimTime(new Date())
                .setStatus2(CommonCache.ORDER_STATUS_2.get("1"))
        );

        return Response.success();
    }

    @ApiOperation("员工提交完成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", readOnly = true, defaultValue = "ZnA6dGVzdGVyMjAyMjAxMDEwMDAwMDAwMQ=="),
            @ApiImplicitParam(name = "id", value = "单子id", dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "接单人id", dataType = "Integer"),
    })
    @PostMapping("finish")
    @AuthCheck(value = {AuthCheck.AuthRole.EMPLOYEE, AuthCheck.AuthRole.ADMIN})
    public Response finish(Integer id) throws Exception {
        final Order model = orderService.getModel(id);
        if (model == null) {
            return Response.info(ResultCodeEnum.OrderNotExist, "");
        }
        if (model.getClaimUserId() == null) {
            return Response.info(ResultCodeEnum.NotClaimOrder, "");
        }
        if (!model.getClaimUserId().equals(RequestUtil.getUserId(request))) {
            return Response.info(ResultCodeEnum.NotSelfOrder, "");
        }

        orderService.update(new Order()
                .setId(id)
                .seteTime(new Date())
                .setStatus3(CommonCache.ORDER_STATUS_3.get("1"))
        );
        return Response.success();
    }
}
