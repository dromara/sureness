package com.usthe.sureness.sample.tom.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Unified message structure definition for front and back ends
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
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * message body data
     */
    private Object data;

    /**
     * exception message when error happen
     */
    private String errorMsg;

}
