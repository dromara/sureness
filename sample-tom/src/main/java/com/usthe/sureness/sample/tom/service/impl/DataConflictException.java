package com.usthe.sureness.sample.tom.service.impl;


/**
 * 请求操作数据与内部数据状态不一致异常
 * @author tomsun28
 * @date 22:55 2020-04-27
 */
public class DataConflictException extends RuntimeException {

    public DataConflictException(String msg) {
        super(msg);
    }
}
