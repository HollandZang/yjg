package com.holland.holland.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
@Data
@Accessors(chain = true)
public class User {

    private Integer id;
    private String user;
    private String pwd;
    private String name;

    @JsonIgnore
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;

    private String[] roleArr;
}
