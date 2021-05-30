package com.holland.holland.common;

import com.holland.holland.util.Response;
import com.holland.holland.util.ResultCodeEnum;
import com.holland.holland.util.ValidateUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response handle(Exception e) {
        e.printStackTrace();
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Response handle(BindException e) {
        e.printStackTrace();
        return Response.info(ResultCodeEnum.ParamException, e.getMessage());
    }

    @ExceptionHandler(value = ValidateUtil.ParameterException.class)
    @ResponseBody
    public Response handle(ValidateUtil.ParameterException e) {
        e.printStackTrace();
        return Response.info(ResultCodeEnum.ParamException, e.getMessage());
    }
}
