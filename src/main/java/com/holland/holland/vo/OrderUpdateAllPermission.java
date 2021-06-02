package com.holland.holland.vo;

import com.holland.holland.pojo.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * zhn
 * 2021-03-01
 */
@ApiModel
public class OrderUpdateAllPermission {

    public Order convert() {
        return new Order()
                .setId(id)
                .setBdUrl(bdUrl)
                .setBdSecret(bdSecret)
                .setDescription(description)
                .setOrder(order)
                .setStatus1(status1)
                .setStatus3(status3)
                .seteTime(eTime)
                ;
    }

    @ApiModelProperty(required = true, example = "1")
    private Integer id;

    @ApiModelProperty(value = "单子的百度网盘地址")
    private String bdUrl;

    @ApiModelProperty(value = "单子的百度网盘提取码", example = "1234")
    private String bdSecret;

    @ApiModelProperty(value = "单子的说明")
    private String description;

//    @ApiModelProperty(value = "创建时间", dataType = "Date", hidden = true)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date cTime;

//    @ApiModelProperty(value = "创建人id", dataType = "Integer", example = "1")
//    private Integer cUserId;
//    @ApiModelProperty(value = "创建人名称", hidden = true)
//    private String cUserName;

    @ApiModelProperty(value = "优先级，0-5，数字越大优先级越高", example = "0")
    private Integer order;

    @ApiModelProperty(value = "单子有效状态 状态1：有效&无效", example = "有效")
    private String status1;

//    @ApiModelProperty(value = "单子接单状态 已接单&未接单", example = "未接单")
//    private String status2;

    @ApiModelProperty(value = "单子完成状态 已完成&未完成", example = "未完成")
    private String status3;

//    @ApiModelProperty(value = "接单人",example = "1")
//    private Integer claimUserId;
//    @ApiModelProperty(value = "接单人", hidden = true)
//    private String claimUserName;

//    @ApiModelProperty(value = "接单时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date claimTime;

    @ApiModelProperty(value = "接单人完成单子的时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime;

    public Integer getId() {
        return id;
    }

    public OrderUpdateAllPermission setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBdUrl() {
        return bdUrl;
    }

    public OrderUpdateAllPermission setBdUrl(String bdUrl) {
        this.bdUrl = bdUrl;
        return this;
    }

    public String getBdSecret() {
        return bdSecret;
    }

    public OrderUpdateAllPermission setBdSecret(String bdSecret) {
        this.bdSecret = bdSecret;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderUpdateAllPermission setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public OrderUpdateAllPermission setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public String getStatus1() {
        return status1;
    }

    public OrderUpdateAllPermission setStatus1(String status1) {
        this.status1 = status1;
        return this;
    }

    public String getStatus3() {
        return status3;
    }

    public OrderUpdateAllPermission setStatus3(String status3) {
        this.status3 = status3;
        return this;
    }

    public Date geteTime() {
        return eTime;
    }

    public OrderUpdateAllPermission seteTime(Date eTime) {
        this.eTime = eTime;
        return this;
    }

}
