package org.dromara.sureness.micronaut.sureness;

import io.micronaut.context.annotation.Factory;

import org.dromara.sureness.matcher.DefaultPathRoleMatcher;
import org.dromara.sureness.matcher.PathTreeProvider;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.micronaut.support.BasicSubjectReactiveCreator;
import org.dromara.sureness.micronaut.support.NoneSubjectReactiveCreator;
import org.dromara.sureness.processor.DefaultProcessorManager;
import org.dromara.sureness.processor.Processor;
import org.dromara.sureness.processor.support.NoneProcessor;
import org.dromara.sureness.processor.support.PasswordProcessor;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentPathTreeProvider;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.SubjectFactory;
import org.dromara.sureness.subject.SurenessSubjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tom
 */
@Factory
public class SurenessConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SurenessConfiguration.class);

    @Singleton
    public SurenessSecurityManager init() {
        SurenessAccountProvider accountProvider = new DocumentAccountProvider();
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        DefaultProcessorManager processorManager = new DefaultProcessorManager(processorList);
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultProcessorManager init");
        }
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProvider(pathTreeProvider);
        pathRoleMatcher.buildTree();
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultPathRoleMatcher init");
        }

        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = Arrays.asList(
                new NoneSubjectReactiveCreator(),
                new BasicSubjectReactiveCreator());
        subjectFactory.registerSubjectCreator(subjectCreates);
        if (logger.isDebugEnabled()) {
            logger.debug("SurenessSubjectFactory init");
        }

        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        if (logger.isDebugEnabled()) {
            logger.debug("SurenessSecurityManager init");
        }
        return securityManager;
    }

}
