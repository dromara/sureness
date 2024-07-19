package org.dromara.sureness.processor;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectSum;

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
