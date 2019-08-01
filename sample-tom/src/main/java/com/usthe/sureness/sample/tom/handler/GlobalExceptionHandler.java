package com.usthe.sureness.sample.tom.handler;

import com.usthe.sureness.sample.tom.pojo.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局controller异常捕获
 * @author tomsun28
 * @date 22:45 2019-08-01
 */
@ControllerAdvice
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
        if (exception != null && exception.getBindingResult() != null
                && exception.getBindingResult().getAllErrors() != null) {
            exception.getBindingResult().getAllErrors().forEach(error ->
                    errorMessage.append(error.getDefaultMessage()));
        }
        if (log.isDebugEnabled()) {
            log.debug("[sample-tom]-[input argument not valid happen]-{}", errorMessage, exception);
        }
        Message message = Message.builder()
                .errorType("input valid").errorMsg(errorMessage.toString()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}
