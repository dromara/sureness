package com.usthe.sureness;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.support.DigestProcessor;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * default sureness config, using file - sureness.yml as persistent layer permissions resources
 * support servlet or jax-rs, default servlet
 * @author tomsun28
 * @date 11:26 2019-05-26
 */
public class DefaultSurenessConfig {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSurenessConfig.class);

    public static final String SUPPORT_SERVLET = "servlet";
    public static final String SUPPORT_JAX_RS = "jax-rs";
    public static final String SUPPORT_SPRING_REACTIVE = "spring-reactive";

    public DefaultSurenessConfig() {
        this.init(SUPPORT_SERVLET);
    }

    public DefaultSurenessConfig(String supportContainer) {
        this.init(supportContainer);
    }

    private void init(String supportContainer) {

        // process init
        // account provider
        SurenessAccountProvider accountProvider = new DocumentAccountProvider();

        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        DigestProcessor digestProcessor = new DigestProcessor();
        digestProcessor.setAccountProvider(accountProvider);
        processorList.add(digestProcessor);
        DefaultProcessorManager processorManager = new DefaultProcessorManager(processorList);
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultProcessorManager init");
        }

        // pathRoleMatcher init
        // pathTree resource provider
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProvider(pathTreeProvider);
        pathRoleMatcher.buildTree();
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultPathRoleMatcher init");
        }

        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates;
        if (SUPPORT_JAX_RS.equals(supportContainer)) {
            subjectCreates = Arrays.asList(
                    new NoneSubjectJaxRsCreator(),
                    new DigestSubjectJaxRsCreator(),
                    new BasicSubjectJaxRsCreator(),
                    new JwtSubjectJaxRsCreator(),
                    new JwtSubjectWsJaxRsCreator());
        } else if (SUPPORT_SPRING_REACTIVE.equals(supportContainer)) {
            subjectCreates = Arrays.asList(
                    new NoneSubjectSpringReactiveCreator(),
                    new DigestSubjectSpringReactiveCreator(),
                    new BasicSubjectSpringReactiveCreator(),
                    new JwtSubjectSpringReactiveCreator(),
                    new JwtSubjectWsSpringReactiveCreator());
        } else {
            subjectCreates = Arrays.asList(
                    new NoneSubjectServletCreator(),
                    new DigestSubjectServletCreator(),
                    new BasicSubjectServletCreator(),
                    new JwtSubjectServletCreator(),
                    new JwtSubjectWsServletCreator());
        }
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
    }
}
