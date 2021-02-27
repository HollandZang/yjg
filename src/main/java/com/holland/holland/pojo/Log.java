package com.holland.holland.pojo;

import lombok.Data;

import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
@Data
public class Log {

    private Integer id;
    private Integer operateUserId;
    private Date operateTime;
    private String operateApi;
    private String ip;

}
