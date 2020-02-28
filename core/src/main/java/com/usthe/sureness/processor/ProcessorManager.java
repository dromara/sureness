package com.usthe.sureness.processor;

import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;

/**
 * 处理器管理
 * @author tomsun28
 * @date 17:53 2019-03-10
 */
public interface ProcessorManager {

    /**
     * description 处理器处理token入口
     *
     * @param auToken 1
     * @return com.usthe.sureness.subject.Subject
     */
    SubjectSum process(Subject auToken);
}
