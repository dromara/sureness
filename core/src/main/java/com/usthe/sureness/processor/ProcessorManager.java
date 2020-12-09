package com.usthe.sureness.processor;

import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;

/**
 * processor manager
 * @author tomsun28
 * @date 17:53 2019-03-10
 */
public interface ProcessorManager {

    /**
     * The entry which processor handles subject
     *
     * @param subject subject
     * @return com.usthe.sureness.subject.Subject
     */
    SubjectSum process(Subject subject);
}
