package com.usthe.sureness.processor;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.chrono.IsoChronology;
import java.util.List;

/**
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
    public Subject process(SubjectAuToken auToken) throws SurenessAuthenticationException, SurenessAuthorizationException {
        checkComponentInit();
        SurenessAuthenticationException lastAuthenticationException = null;
        SurenessAuthorizationException lastAuthorizationException = null;
        Subject subjectResult = null;
        Class<? extends SubjectAuToken> auTokenClazz = auToken.getClass();

        // 对process链  一个process成功即可
        for (Processor processor : getProcessorList()) {
            if (processor.canSupportAuTokenClass(auTokenClazz)) {
                try {
                    subjectResult = processor.process(auToken);
                } catch (SurenessAuthenticationException var1) {
                    lastAuthenticationException = var1;
                } catch (SurenessAuthorizationException var2) {
                    lastAuthorizationException = var2;
                }
            }
            // 如果此次process 认证鉴权成功返回结果
            if (subjectResult != null) {
                return subjectResult;
            }
        }
        // 如果最终异常都为空 ,说明没有processor匹配到token
        if (lastAuthenticationException == null && lastAuthorizationException == null) {
            throw new ProcessorNotFoundException("not found token : " + auToken.getClass()
                    + " --target processor");
        }
        throw lastAuthenticationException == null? lastAuthorizationException : lastAuthenticationException;
    }

    private List<Processor> getProcessorList() {
        return processorList;
    }

    public void setProcessorList(List<Processor> processorList) {
        this.processorList = processorList;
    }

    public void checkComponentInit() throws SurenessNoInitException{
        if (this.processorList == null) {
            throw new SurenessNoInitException("processorManager not init component processorList");
        }
    }
}
