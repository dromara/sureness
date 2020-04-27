package com.usthe.sureness.sample.tom.handler;

import com.usthe.sureness.sample.tom.pojo.dto.Message;
import com.usthe.sureness.sample.tom.service.impl.DataConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局controller异常捕获
 * @author tomsun28
 * @date 22:45 2019-08-01
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 对于所有接口入参数据校验抛出的异常统一处理
     * @param exception 入参数据校验异常
     * @return 统一错误信息体
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<Message> handleInputValidException(MethodArgumentNotValidException exception) {
        StringBuffer errorMessage = new StringBuffer();
        if (exception != null) {
            exception.getBindingResult().getAllErrors().forEach(error ->
                    errorMessage.append(error.getDefaultMessage()).append("."));
        }
        if (log.isDebugEnabled()) {
            log.debug("[sample-tom]-[input argument not valid happen]-{}", errorMessage, exception);
        }
        Message message = Message.builder().errorMsg(errorMessage.toString()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * 对于所有数据库dao操作的异常统一处理
     * @param exception 数据库异常
     * @return 统一错误信息体
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    ResponseEntity<Message> handleDataAccessException(DataAccessException exception) {
        String errorMessage = "database error happen";
        if (exception != null) {
            errorMessage = exception.getMessage();
        }
        log.warn("[sample-tom]-[database error happen]-{}", errorMessage, exception);
        Message message = Message.builder().errorMsg(errorMessage).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    /**
     * 对于请求数据和系统数据状态不一致异常统一处理
     * @param exception 数据状态不一致异常
     * @return 统一错误信息体
     */
    @ExceptionHandler(DataConflictException.class)
    @ResponseBody
    ResponseEntity<Message> handleDataConflictException(DataConflictException exception) {
        String errorMessage = "data status conflict warning";
        if (exception != null && exception.getMessage() != null) {
            errorMessage = exception.getMessage();
        }
        log.info("[sample-tom]-[data status conflict warning]-{}", errorMessage, exception);
        Message message = Message.builder().errorMsg(errorMessage).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    /**
     * 对所以未捕获未知异常统一处理
     * @param exception UnknownException
     * @return 统一错误信息体
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<Message> handleUnknownException(Exception exception) {
        String errorMessage = "unknown error happen";
        if (exception != null) {
            errorMessage = exception.getMessage();
        }
        log.error("[sample-tom]-[unknown error happen]-{}", errorMessage, exception);
        Message message = Message.builder().errorMsg(errorMessage).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}
