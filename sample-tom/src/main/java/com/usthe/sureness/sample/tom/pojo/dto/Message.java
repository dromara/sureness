package com.usthe.sureness.sample.tom.pojo.dto;

import lombok.Builder;
import lombok.Data;

/**
 *  前后端http api统一消息定义协议 Message
 *
 * {
 *   data:{....},
 *   errorMsg: message
 * }
 * @author tomsun28
 * @date 23:48 2019/08/01
 */
@Data
@Builder
public class Message {

    /**
     * 消息内容  存储对象数据
     */
    private Object data;

    /**
     * 发生异常时信息
     */
    private String errorMsg;

}
