package org.dromara.sureness.sample.tom.sureness.config;

import org.dromara.sureness.matcher.DefaultPathRoleMatcher;
import org.dromara.sureness.matcher.PathTreeProvider;
import org.dromara.sureness.matcher.TreePathRoleMatcher;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.DefaultProcessorManager;
import org.dromara.sureness.processor.Processor;
import org.dromara.sureness.processor.ProcessorManager;
import org.dromara.sureness.processor.support.JwtProcessor;
import org.dromara.sureness.processor.support.NoneProcessor;
import org.dromara.sureness.processor.support.PasswordProcessor;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.provider.annotation.AnnotationPathTreeProvider;
import org.dromara.sureness.provider.ducument.DocumentPathTreeProvider;
import org.dromara.sureness.sample.tom.sureness.processor.CustomTokenProcessor;
import org.dromara.sureness.sample.tom.sureness.subject.CustomPasswdSubjectCreator;
import org.dromara.sureness.sample.tom.sureness.subject.CustomTokenSubjectCreator;
import org.dromara.sureness.subject.SubjectFactory;
import org.dromara.sureness.subject.SurenessSubjectFactory;
import org.dromara.sureness.subject.creater.BasicSubjectServletCreator;
import org.dromara.sureness.subject.creater.JwtSubjectServletCreator;
import org.dromara.sureness.subject.creater.NoneSubjectServletCreator;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * sureness config
 * @author tomsun28
 * @date 22:40 2020-03-02
 */
@Configuration
public class SurenessConfiguration {

    /**
     * jwt secret key
     */
    private static final String TOM_SECRET_KEY = "?::4s9ssf2sf4sed45pf):" +
            "RnLN7XNn4wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
            "CyaFv0bwq2Eik0jdrKUtsA6bx3sDJeFV643R+YYzGMRIqcBIp6AKA98" +
            "GM2RIqcBIp6-?::4390fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546";

    @Bean
    ProcessorManager processorManager(SurenessAccountProvider accountProvider) {
        // process init
        List<Processor> processorList = new LinkedList<>();
        // use default none processor
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        // use default jwt processor
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        // use default basic auth processor
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);

        // use custom token processor
        CustomTokenProcessor customTokenProcessor = new CustomTokenProcessor();
        customTokenProcessor.setAccountProvider(accountProvider);
        processorList.add(customTokenProcessor);
        return new DefaultProcessorManager(processorList);
    }

    /**
     * @param databasePathTreeProvider the path tree resource load from database
     */
    @Bean
    TreePathRoleMatcher pathRoleMatcher(PathTreeProvider databasePathTreeProvider) {
        // the path tree resource load from document - sureness.yml
        PathTreeProvider documentPathTreeProvider = new DocumentPathTreeProvider();
        // the path tree resource load form annotation - @RequiresRoles @WithoutAuth
        AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
        annotationPathTreeProvider.setScanPackages(Collections.singletonList("com.usthe.sureness.sample.tom.controller"));
        // pathRoleMatcher init
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProviderList(Arrays.asList(
                documentPathTreeProvider,
                annotationPathTreeProvider,
                databasePathTreeProvider));
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
                // use default basic auth subject creator
                new BasicSubjectServletCreator(),
                // use default jwt subject creator
                new JwtSubjectServletCreator(),
                // use custom password creator
                new CustomPasswdSubjectCreator(),
                // use custom token creator
                new CustomTokenSubjectCreator()));
        return subjectFactory;
    }

    @Bean
    SurenessSecurityManager securityManager(ProcessorManager processorManager,
                                            TreePathRoleMatcher pathRoleMatcher, SubjectFactory subjectFactory) {
        JsonWebTokenUtil.setDefaultSecretKey(TOM_SECRET_KEY);
        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        return securityManager;
    }

}
