package com.usthe.sureness.processor;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * sureness default process manager
 * @author tomsun28
 * @date 22:21 2019-03-10
 */
public class DefaultProcessorManager implements ProcessorManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultProcessorManager.class);

    private List<Processor> processorList;

    public DefaultProcessorManager(List<Processor> processorList) {
        this.processorList = processorList;
    }

    @Override
    public SubjectSum process(Subject subject) throws SurenessAuthenticationException, SurenessAuthorizationException {
        checkComponentInit();
        SurenessAuthenticationException lastAuthenticationException = null;
        SurenessAuthorizationException lastAuthorizationException = null;
        SubjectSum subjectResult = null;
        Class<? extends Subject> subjectClazz = subject.getClass();

        // Process chain cyclic processing, one process can be successful
        for (Processor processor : getProcessorList()) {
            if (processor.canSupportSubjectClass(subjectClazz)) {
                try {
                    subjectResult = processor.process(subject);
                } catch (SurenessAuthenticationException var1) {
                    lastAuthenticationException = var1;
                } catch (SurenessAuthorizationException var2) {
                    lastAuthorizationException = var2;
                }
            }
            // if process auth success, return
            if (subjectResult != null) {
                return subjectResult;
            }
        }
        // if last exception is null, means that no processor matches the subject
        if (lastAuthenticationException == null && lastAuthorizationException == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("not found token : {} -- target processor", subject.getClass() );
            }
            throw new ProcessorNotFoundException("not found token : " + subject.getClass()
                    + " --target processor");
        }
        throw lastAuthenticationException == null ? lastAuthorizationException : lastAuthenticationException;
    }

    private List<Processor> getProcessorList() {
        return processorList;
    }

    private void checkComponentInit() throws SurenessNoInitException{
        if (this.processorList == null) {
            throw new SurenessNoInitException("processorManager not init component processorList");
        }
    }
}
