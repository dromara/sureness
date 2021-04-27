
package com.sureness.micronaut.sureness;


import com.usthe.sureness.handler.AttachSessionServletHandler;
import com.usthe.sureness.handler.HandlerManager;
import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.processor.support.SessionProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.annotation.AnnotationPathTreeProvider;
import com.usthe.sureness.provider.ducument.DocumentAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.BasicSubjectServletCreator;
import com.usthe.sureness.subject.creater.NoneSubjectServletCreator;
import com.usthe.sureness.subject.creater.SessionSubjectServletCreator;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Configuration;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Singleton
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
