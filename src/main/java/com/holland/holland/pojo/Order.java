package com.holland.holland.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
@ApiModel
public class Order {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(value = "单子的百度网盘地址")
    private String bdUrl;

    @ApiModelProperty(value = "优先级，0-5，数字越大优先级越高", hidden = true)
    private Integer order;

    @ApiModelProperty(value = "单子的百度网盘提取码", example = "1234")
    private String bdSecret;

    @ApiModelProperty(value = "单子的说明")
    private String description;

    @ApiModelProperty(value = "创建时间", dataType = "Date", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;

    @ApiModelProperty(value = "创建人id", dataType = "Integer", example = "1", hidden = true)
    private Integer cUserId;
    @ApiModelProperty(value = "创建人名称", hidden = true)
    private String cUserName;

    @ApiModelProperty(value = "单子有效状态 状态1：有效&无效", example = "有效", hidden = true)
    private String status1;

    @ApiModelProperty(value = "单子接单状态 已接单&未接单", example = "未接单", hidden = true)
    private String status2;

    @ApiModelProperty(value = "单子完成状态 已完成&未完成", example = "未完成", hidden = true)
    private String status3;

    @ApiModelProperty(value = "谁接了这个单子", hidden = true)
    private Integer claimUserId;
    @ApiModelProperty(value = "谁接了这个单子", hidden = true)
    private String claimUserName;

    @ApiModelProperty(value = "接单时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date claimTime;

    @ApiModelProperty(value = "接单人完成单子的时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime;

    public Integer getId() {
        return id;
    }

    public Order setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public Order setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public String getBdUrl() {
        return bdUrl;
    }

    public Order setBdUrl(String bdUrl) {
        this.bdUrl = bdUrl;
        return this;
    }

    public String getBdSecret() {
        return bdSecret;
    }

    public Order setBdSecret(String bdSecret) {
        this.bdSecret = bdSecret;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Order setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getcTime() {
        return cTime;
    }

    public Order setcTime(Date cTime) {
        this.cTime = cTime;
        return this;
    }

    public Integer getcUserId() {
        return cUserId;
    }

    public Order setcUserId(Integer cUserId) {
        this.cUserId = cUserId;
        return this;
    }

    public String getStatus1() {
        return status1;
    }

    public Order setStatus1(String status1) {
        this.status1 = status1;
        return this;
    }

    public String getStatus2() {
        return status2;
    }

    public Order setStatus2(String status2) {
        this.status2 = status2;
        return this;
    }

    public String getStatus3() {
        return status3;
    }

    public Order setStatus3(String status3) {
        this.status3 = status3;
        return this;
    }

    public Integer getClaimUserId() {
        return claimUserId;
    }

    public Order setClaimUserId(Integer claimUserId) {
        this.claimUserId = claimUserId;
        return this;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public Order setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
        return this;
    }

    public Date geteTime() {
        return eTime;
    }

    public Order seteTime(Date eTime) {
        this.eTime = eTime;
        return this;
    }

    public String getcUserName() {
        return cUserName;
    }

    public Order setcUserName(String cUserName) {
        this.cUserName = cUserName;
        return this;
    }

    public String getClaimUserName() {
        return claimUserName;
    }

    public Order setClaimUserName(String claimUserName) {
        this.claimUserName = claimUserName;
        return this;
    }
}
