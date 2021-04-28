package com.usthe.sureness.micronaut.sureness;

import com.usthe.sureness.micronaut.support.BasicSubjectReactiveCreator;
import com.usthe.sureness.micronaut.support.NoneSubjectReactiveCreator;
import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import io.micronaut.context.annotation.Factory;
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
