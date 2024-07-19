package org.dromara.sureness.sample.session.sureness;


import org.dromara.sureness.handler.AttachSessionServletHandler;
import org.dromara.sureness.handler.HandlerManager;
import org.dromara.sureness.matcher.DefaultPathRoleMatcher;
import org.dromara.sureness.matcher.PathTreeProvider;
import org.dromara.sureness.matcher.TreePathRoleMatcher;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.DefaultProcessorManager;
import org.dromara.sureness.processor.Processor;
import org.dromara.sureness.processor.ProcessorManager;
import org.dromara.sureness.processor.support.NoneProcessor;
import org.dromara.sureness.processor.support.PasswordProcessor;
import org.dromara.sureness.processor.support.SessionProcessor;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.provider.annotation.AnnotationPathTreeProvider;
import org.dromara.sureness.provider.ducument.DocumentAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentPathTreeProvider;
import org.dromara.sureness.subject.SubjectFactory;
import org.dromara.sureness.subject.SurenessSubjectFactory;
import org.dromara.sureness.subject.creater.BasicSubjectServletCreator;
import org.dromara.sureness.subject.creater.NoneSubjectServletCreator;
import org.dromara.sureness.subject.creater.SessionSubjectServletCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * sureness config,Use DefaultSurenessConfig
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Configuration
public class SurenessConfiguration {


    @Bean
    ProcessorManager processorManager() {
        // process init
        List<Processor> processorList = new LinkedList<>();
        // use default none processor
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        // use default basic auth processor
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        SurenessAccountProvider accountProvider = new DocumentAccountProvider();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);

        // use session auth processor
        SessionProcessor sessionProcessor = new SessionProcessor();
        processorList.add(sessionProcessor);

        return new DefaultProcessorManager(processorList);
    }


    @Bean
    TreePathRoleMatcher pathRoleMatcher() {
        // the path tree resource load from document - sureness.yml
        PathTreeProvider documentPathTreeProvider = new DocumentPathTreeProvider();
        // the path tree resource load form annotation - @RequiresRoles @WithoutAuth
        AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
        annotationPathTreeProvider.setScanPackages(Collections.singletonList("com.usthe.sureness.sample.tom.controller"));
        // pathRoleMatcher init
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProviderList(Arrays.asList(
                documentPathTreeProvider,
                annotationPathTreeProvider));
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    SubjectFactory subjectFactory() {
        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        subjectFactory.registerSubjectCreator(Arrays.asList(
                // attention! must add noSubjectCreator first
                new NoneSubjectServletCreator(),
                // use default session jwt subject creator
                new SessionSubjectServletCreator(),
                // use default basic auth subject creator
                new BasicSubjectServletCreator()));
        return subjectFactory;
    }

    @Bean
    HandlerManager successHandlerManager() {
        HandlerManager handlerManager = new HandlerManager();
        handlerManager.registerHandler(new AttachSessionServletHandler());
        return handlerManager;
    }

    @Bean
    SurenessSecurityManager securityManager(ProcessorManager processorManager,
                                            TreePathRoleMatcher pathRoleMatcher,
                                            SubjectFactory subjectFactory,
                                            HandlerManager handlerManager) {
        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        securityManager.setHandlerManager(handlerManager);
        return securityManager;
    }

}
