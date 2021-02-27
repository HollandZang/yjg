package com.holland.holland.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
@ApiModel
@Data
@Accessors(chain = true)
public class Order {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(value = "单子的百度网盘地址")
    private String bdUrl;

    @ApiModelProperty(value = "单子的百度网盘提取码", example = "1234")
    private String bdSecret;

    @ApiModelProperty(value = "单子的说明")
    private String description;

    @ApiModelProperty(value = "创建时间", dataType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;

    @ApiModelProperty(value = "创建人id", dataType = "Integer")
    private Integer cUserId;

    @ApiModelProperty(value = "单子状态", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "谁接了这个单子", hidden = true)
    private Integer claimUserId;

    @ApiModelProperty(value = "接单时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date claimTime;

    @ApiModelProperty(value = "接单人完成单子的时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime;

    @ApiModelProperty(value = "审核确认接单人真的完成的时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date verityTime;

    @ApiModelProperty(value = "审核人", hidden = true)
    private Integer verifyUserId;
}
