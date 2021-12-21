package com.usthe.sureness.configuration;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.mgt.SecurityManager;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.DigestProcessor;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.processor.support.SessionProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.annotation.AnnotationPathTreeProvider;
import com.usthe.sureness.provider.ducument.DocumentAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.BasicSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.BasicSubjectServletCreator;
import com.usthe.sureness.subject.creater.BasicSubjectSpringReactiveCreator;
import com.usthe.sureness.subject.creater.DigestSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.DigestSubjectServletCreator;
import com.usthe.sureness.subject.creater.DigestSubjectSpringReactiveCreator;
import com.usthe.sureness.subject.creater.JwtSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.JwtSubjectServletCreator;
import com.usthe.sureness.subject.creater.JwtSubjectSpringReactiveCreator;
import com.usthe.sureness.subject.creater.JwtSubjectWsJaxRsCreator;
import com.usthe.sureness.subject.creater.JwtSubjectWsServletCreator;
import com.usthe.sureness.subject.creater.JwtSubjectWsSpringReactiveCreator;
import com.usthe.sureness.subject.creater.NoneSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.NoneSubjectServletCreator;
import com.usthe.sureness.subject.creater.NoneSubjectSpringReactiveCreator;
import com.usthe.sureness.subject.creater.SessionSubjectServletCreator;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.usthe.sureness.configuration.SurenessProperties.*;


/**
 * @author wangtao
 * @date 2021/7/3
 */
@Configuration
@ConditionalOnProperty(prefix = "sureness", name = "enable", havingValue = "true",
        matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@AutoConfigureAfter(value = {SurenessProperties.class})
public class SurenessAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurenessAutoConfiguration.class);

    private SurenessProperties surenessProperties;

    public SurenessAutoConfiguration(SurenessProperties properties) {
        this.surenessProperties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(SurenessAccountProvider.class)
    SurenessAccountProvider accountProvider(){
        return new DocumentAccountProvider();
    }

    @Bean
    @ConditionalOnMissingBean(PathTreeProvider.class)
    PathTreeProvider pathTreeProvider(){
        return new DocumentPathTreeProvider();
    }

    @Bean
    @ConditionalOnMissingBean(SubjectFactory.class)
    @ConditionalOnBean(SurenessProperties.class)
    SubjectFactory subjectFactory() {
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = new ArrayList<>();
        AuthType[] authTypeArr = surenessProperties.getAuths();
        Set<AuthType> authTypes = authTypeArr == null ? new HashSet<>() : new HashSet<>(Arrays.asList(authTypeArr));
        if (authTypes.isEmpty()) {
            // if is null, default config is basic auth, jwt auth
            LOGGER.info("[sureness-starter] - use default authTypes: Basic, Jwt");
            authTypes = new HashSet<>(2);
            authTypes.add(AuthType.BASIC);
            authTypes.add(AuthType.JWT);
        }
        ContainerType containerType = surenessProperties.getContainer();
        if (containerType == null) {
            // if is null, default config is servlet
            LOGGER.info("[sureness-starter] - use default supportTypes: Servlet, Websocket");
            containerType = ContainerType.Servlet;
        }
        boolean enableWebsocket = surenessProperties.getWebsocket() != null
                && surenessProperties.getWebsocket().isEnable();
        switch (containerType) {
            case Servlet:
                subjectCreates.add(new NoneSubjectServletCreator());
                if (enableWebsocket) {
                    subjectCreates.add(new JwtSubjectWsServletCreator());
                }
                if (authTypes.contains(AuthType.BASIC)){
                    subjectCreates.add(new BasicSubjectServletCreator());
                }
                if (authTypes.contains(AuthType.JWT)){
                    subjectCreates.add(new JwtSubjectServletCreator());
                    subjectCreates.add(new JwtSubjectWsServletCreator());
                }
                if (authTypes.contains(AuthType.DIGEST)){
                    subjectCreates.add(new DigestSubjectServletCreator());
                }
                if (surenessProperties.getWebsocket() != null && surenessProperties.getWebsocket().isEnable()) {
                    subjectCreates.add(new SessionSubjectServletCreator());
                }
                break;
            case JAX_RS:
                subjectCreates.add(new NoneSubjectJaxRsCreator());
                if (enableWebsocket) {
                    subjectCreates.add(new JwtSubjectWsJaxRsCreator());
                }
                if (authTypes.contains(AuthType.BASIC)){
                    subjectCreates.add(new BasicSubjectJaxRsCreator());
                }
                if (authTypes.contains(AuthType.JWT)){
                    subjectCreates.add(new JwtSubjectJaxRsCreator());
                    subjectCreates.add(new JwtSubjectWsJaxRsCreator());
                }
                if (authTypes.contains(AuthType.DIGEST)){
                    subjectCreates.add(new DigestSubjectJaxRsCreator());
                }
                break;
            case Spring_Reactor:
                subjectCreates.add(new NoneSubjectSpringReactiveCreator());
                if (enableWebsocket) {
                    subjectCreates.add(new JwtSubjectWsSpringReactiveCreator());
                }
                if (authTypes.contains(AuthType.BASIC)) {
                    subjectCreates.add(new BasicSubjectSpringReactiveCreator());
                }
                if (authTypes.contains(AuthType.JWT)) {
                    subjectCreates.add(new JwtSubjectSpringReactiveCreator());
                }
                if (authTypes.contains(AuthType.DIGEST)) {
                    subjectCreates.add(new DigestSubjectSpringReactiveCreator());
                }
            default: break;
        }
        subjectFactory.registerSubjectCreator(subjectCreates);
        LOGGER.info("[sureness-starter] - SurenessSubjectFactory init success");
        return subjectFactory;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityManager.class)
    SecurityManager securityManager(
            ProcessorManager processorManager,
            TreePathRoleMatcher pathRoleMatcher,
            SubjectFactory subjectFactory) {
        if (surenessProperties.getJwt() != null) {
            String jwtSecret = surenessProperties.getJwt().getSecret();
            if (jwtSecret != null && !"".equals(jwtSecret)) {
                JsonWebTokenUtil.setDefaultSecretKey(jwtSecret);
            }
        }
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        return securityManager;
    }


    @Bean
    @ConditionalOnMissingBean(ProcessorManager.class)
    ProcessorManager processorManager(SurenessAccountProvider accountProvider) {
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        AuthType[] authTypeArr = surenessProperties.getAuths();
        Set<AuthType> authTypes = authTypeArr == null ? new HashSet<>() : new HashSet<>(Arrays.asList(authTypeArr));
        if (authTypes.isEmpty()) {
            // if is null, default config is basic auth, jwt auth
            LOGGER.info("[sureness-starter] - use default authTypes: Basic, Jwt");
            authTypes = new HashSet<>(2);
            authTypes.add(AuthType.BASIC);
            authTypes.add(AuthType.JWT);
        }
        if (authTypes.contains(AuthType.JWT)) {
            JwtProcessor jwtProcessor = new JwtProcessor();
            processorList.add(jwtProcessor);
        }
        if (authTypes.contains(AuthType.BASIC)) {
            PasswordProcessor passwordProcessor = new PasswordProcessor();
            passwordProcessor.setAccountProvider(accountProvider);
            processorList.add(passwordProcessor);
        }
        if (authTypes.contains(AuthType.DIGEST)) {
            DigestProcessor digestProcessor = new DigestProcessor();
            digestProcessor.setAccountProvider(accountProvider);
            processorList.add(digestProcessor);
        }
        if (surenessProperties.getSession() != null && surenessProperties.getSession().isEnable()) {
            SessionProcessor sessionProcessor = new SessionProcessor();
            processorList.add(sessionProcessor);
        }
        return new DefaultProcessorManager(processorList);
    }

    @Bean
    @ConditionalOnMissingBean(TreePathRoleMatcher.class)
    TreePathRoleMatcher pathRoleMatcher(List<PathTreeProvider> pathTreeProviders) {
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        if (pathTreeProviders == null) {
            pathTreeProviders = new ArrayList<>();
        }
        if (pathTreeProviders.isEmpty()) {
            // add documentProvider default
            pathTreeProviders.add(new DocumentPathTreeProvider());
        }
        AnnotationProperties annotationProperties = surenessProperties.getAnnotation();
        if (annotationProperties != null && annotationProperties.isEnable()) {
            List<String> scanPackages = annotationProperties.getScanPackages();
            if (scanPackages == null || scanPackages.isEmpty()) {
                LOGGER.error("[sureness-starter] - annotation is enable but annotation.scanPackages is null, need config!");
            } else {
                AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
                annotationPathTreeProvider.setScanPackages(scanPackages);
                pathTreeProviders.add(annotationPathTreeProvider);
            }
        }
        pathRoleMatcher.setPathTreeProviderList(pathTreeProviders);
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnExpression("'${sureness.container:servlet}'.equalsIgnoreCase('servlet')")
    public FilterRegistrationBean<SurenessFilter> filterRegistration(SecurityManager securityManager) {
        SurenessFilter surenessFilter = new SurenessFilter(securityManager);
        FilterRegistrationBean<SurenessFilter> registration = new FilterRegistrationBean<>();
        registration.addUrlPatterns("/*");
        registration.setFilter(surenessFilter);
        registration.setName("SurenessFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
