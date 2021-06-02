package com.holland.holland.vo;

import com.holland.holland.pojo.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * zhn
 * 2021-03-01
 */
@ApiModel
public class OrderUpdateCustomer {

    public Order convert() {
        return new Order()
                .setId(id)
                .setBdUrl(bdUrl)
                .setBdSecret(bdSecret)
                .setDescription(description)
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

    public Integer getId() {
        return id;
    }

    public OrderUpdateCustomer setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBdUrl() {
        return bdUrl;
    }

    public OrderUpdateCustomer setBdUrl(String bdUrl) {
        this.bdUrl = bdUrl;
        return this;
    }

    public String getBdSecret() {
        return bdSecret;
    }

    public OrderUpdateCustomer setBdSecret(String bdSecret) {
        this.bdSecret = bdSecret;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderUpdateCustomer setDescription(String description) {
        this.description = description;
        return this;
    }
}
