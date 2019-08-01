package com.usthe.sureness.sample.tom.pojo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 *  前后端http api统一消息定义协议 Message
 *
 * {
 *   meta:{"code":code,“msg”:message},
 *   data:{....},
 *   T,
 *   errorMsg: message,
 *   errorType: type
 * }
 * @author tomsun28
 * @date 23:48 2019/08/01
 */
@Data
@Builder
public class Message<T> {

    /**
     * 消息头meta 存放状态信息 code message
     */
    private Map<String, String> meta;

    /**
     * 消息内容  存储对象数据
     */
    private T body;

    /**
     * 消息内容  存储一些便捷简单的键值string数据
     */
    private Map<String, String> data;

    /**
     * 发生异常时信息
     */
    private String errorMsg;

    /**
     * 异常的类型
     */
    private String errorType;

}
