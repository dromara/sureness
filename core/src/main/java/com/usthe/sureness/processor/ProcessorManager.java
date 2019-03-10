package com.usthe.sureness.processor;

import com.usthe.sureness.subject.SubjectAuToken;

/**
 * @author tomsun28
 * @date 17:53 2019-03-10
 */
public interface ProcessorManager {


    /**
     * description 处理器处理token入口
     * @param auToken 1
     */
    void process(SubjectAuToken auToken);
}
