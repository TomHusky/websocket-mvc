package com.lwj.websocketmvc.exception;


import com.lwj.websocketmvc.util.ApiResult;
import com.lwj.websocketmvc.util.ApiResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 异常捕获处理器
 *
 * @author gzfyit
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 统一错误
     */
    @ExceptionHandler(Exception.class)
    public ApiResult exception(Exception e) {
        logger.error(e.getMessage());
        logger.error(Arrays.toString(e.getStackTrace()));
        logger.error(e.getClass().getName());
        return ApiResultUtil.error(10009, "失败", e.getMessage());
    }

    @ExceptionHandler({AppKeyInvalidException.class})
    public ApiResult otherException(Exception e) {
        logger.error(e.getMessage());
        logger.error(Arrays.toString(e.getStackTrace()));
        logger.error(e.getClass().getName());
        return ApiResultUtil.error(10001, "失败", e.getMessage());
    }
    /**
     * 处理实体字段校验不通过异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrorList) {
            builder.append(error.getDefaultMessage());
        }
        return ApiResultUtil.error(10006, builder.toString());
    }

}