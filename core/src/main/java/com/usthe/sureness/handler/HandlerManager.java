package com.usthe.sureness.handler;

import com.usthe.sureness.subject.SubjectSum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * the manager for successHandler
 * @author tomsun28
 * @date 2021/4/5 17:43
 */
public class HandlerManager {

    private static final Logger log = LoggerFactory.getLogger(HandlerManager.class);

    private List<SuccessHandler> successHandlers;

    /**
     * hand after success processor
     * @param subjectSum subject
     * @param request http request
     */
    public void hand(SubjectSum subjectSum, Object request) {
        if (successHandlers != null) {
            for (SuccessHandler successHandler : successHandlers) {
                try {
                    successHandler.processHandler(subjectSum, request);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public void registerHandler(List<SuccessHandler> handlers) {
        if (successHandlers == null) {
            successHandlers = handlers;
        } else {
            successHandlers.addAll(handlers);
        }
    }

    public void registerHandler(SuccessHandler handler) {
        if (successHandlers == null) {
            successHandlers = new LinkedList<>();
        }
        successHandlers.add(handler);
    }
}
