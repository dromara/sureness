package org.dromara.sureness.solon;


import org.dromara.sureness.matcher.DefaultPathRoleMatcher;
import org.dromara.sureness.matcher.PathTreeProvider;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.DefaultProcessorManager;
import org.dromara.sureness.processor.Processor;
import org.dromara.sureness.processor.support.DigestProcessor;
import org.dromara.sureness.processor.support.JwtProcessor;
import org.dromara.sureness.processor.support.NoneProcessor;
import org.dromara.sureness.processor.support.PasswordProcessor;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentPathTreeProvider;
import org.dromara.sureness.solon.support.BasicSubjectSolonCreator;
import org.dromara.sureness.solon.support.DigestSubjectSolonCreator;
import org.dromara.sureness.solon.support.JwtSubjectSolonCreator;
import org.dromara.sureness.solon.support.NoneSubjectSolonCreator;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.SubjectFactory;
import org.dromara.sureness.subject.SurenessSubjectFactory;
import org.noear.solon.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * sureness config,Use DefaultSurenessConfig
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Configuration
public class SurenessConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SurenessConfiguration.class);

    public SurenessConfiguration() {
        init();
    }

    private void init() {

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
        List<SubjectCreate> subjectCreates = Arrays.asList(
                new NoneSubjectSolonCreator(),
                new DigestSubjectSolonCreator(),
                new BasicSubjectSolonCreator(),
                new JwtSubjectSolonCreator());
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
