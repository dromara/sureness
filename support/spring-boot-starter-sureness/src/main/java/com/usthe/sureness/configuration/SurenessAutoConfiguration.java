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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

import java.util.ArrayList;
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
@ConditionalOnProperty(prefix = "sureness", name = "enabled", havingValue = "true",
        matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@AutoConfigureAfter(value = {SurenessProperties.class})
public class SurenessAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurenessAutoConfiguration.class);

    private static final int NUM_2 = 2;

    private ApplicationContext applicationContext;

    private SurenessProperties surenessProperties;

    public SurenessAutoConfiguration(ApplicationContext applicationContext, SurenessProperties properties) {
        this.applicationContext = applicationContext;
        this.surenessProperties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(SurenessAccountProvider.class)
    SurenessAccountProvider accountProvider(){
        return new DocumentAccountProvider();
    }

    @Bean
    @ConditionalOnMissingBean(PathTreeProvider.class)
    PathTreeProvider pathTreeProvider(DefaultPathRoleMatcher pathRoleMatcher){
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        pathRoleMatcher.setPathTreeProvider(pathTreeProvider);
        pathRoleMatcher.buildTree();
        return pathTreeProvider;
    }

    @Bean
    @ConditionalOnMissingBean(SubjectFactory.class)
    @ConditionalOnBean(SurenessProperties.class)
    SubjectFactory subjectFactory() {
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = new ArrayList<>();
        Set<AuthType> authTypes = surenessProperties.getAuthTypes();
        if (authTypes == null || authTypes.isEmpty()) {
            // if is null, default config is basic auth, jwt auth
            LOGGER.info("[sureness-starter] - use default authTypes: Basic, Jwt");
            authTypes = new HashSet<>(2);
            authTypes.add(AuthType.BASIC);
            authTypes.add(AuthType.JWT);
            surenessProperties.setAuthTypes(authTypes);
        }
        Set<SupportType> supportTypes = surenessProperties.getSupportTypes();
        if (supportTypes == null || supportTypes.isEmpty()) {
            // if is null, default config is servlet, websocket
            LOGGER.info("[sureness-starter] - use default supportTypes: Servlet, Websocket");
            supportTypes = new HashSet<>(2);
            supportTypes.add(SupportType.Servlet);
            supportTypes.add(SupportType.WebSocket);
            surenessProperties.setSupportTypes(supportTypes);
        }
        if (supportTypes.size() >= NUM_2 && !supportTypes.contains(SupportType.WebSocket)) {
            LOGGER.error("[sureness-starter] - supportTypes: Servlet, JAX-RS or Spring-Reactor neither can exist at the same time");
            throw new SurenessInitException("[sureness-starter] - supportTypes: Servlet, JAX-RS or Spring-Reactor neither can exist at the same time");
        }
        if (supportTypes.contains(SupportType.Servlet)) {
            subjectCreates.add(new NoneSubjectServletCreator());
            if (supportTypes.contains(SupportType.WebSocket)) {
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
            if (surenessProperties.isSessionEnabled()) {
                subjectCreates.add(new SessionSubjectServletCreator());
            }
        } else if (supportTypes.contains(SupportType.JAX_RS)){
            // other is JAX-RS
            subjectCreates.add(new NoneSubjectJaxRsCreator());
            if (supportTypes.contains(SupportType.WebSocket)) {
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
        } else if (supportTypes.contains(SupportType.Spring_Reactor)) {
            subjectCreates.add(new NoneSubjectSpringReactiveCreator());
            if (supportTypes.contains(SupportType.WebSocket)) {
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
            String jwtSecret = surenessProperties.getJwt().getSecretKey();
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
        Set<AuthType> authTypes = surenessProperties.getAuthTypes();
        if (authTypes == null || authTypes.isEmpty()) {
            // if is null, default config is basic auth, jwt auth
            LOGGER.info("[sureness-starter] - use default authTypes: Basic, Jwt");
            authTypes = new HashSet<>(2);
            authTypes.add(AuthType.BASIC);
            authTypes.add(AuthType.JWT);
            surenessProperties.setAuthTypes(authTypes);
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
        if (surenessProperties.isSessionEnabled()) {
            SessionProcessor sessionProcessor = new SessionProcessor();
            processorList.add(sessionProcessor);
        }
        return new DefaultProcessorManager(processorList);
    }

    @Bean
    @ConditionalOnMissingBean(TreePathRoleMatcher.class)
    TreePathRoleMatcher pathRoleMatcher(PathTreeProvider pathTreeProvider,
                                        DefaultPathRoleMatcher pathRoleMatcher) {
        List<PathTreeProvider> providers = new ArrayList<>();
        providers.add(pathTreeProvider);
        // add documentProvider default
        providers.add(new DocumentPathTreeProvider());
        AnnotationProperties annotationProperties = surenessProperties.getAnnotation();
        if (annotationProperties != null && annotationProperties.isEnable()) {
            List<String> scanPackages = annotationProperties.getScanPackages();
            if (scanPackages == null || scanPackages.isEmpty()) {
                LOGGER.error("[sureness-starter] - annotation is enable but annotation.scanPackages is null, need config!");
            } else {
                AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
                annotationPathTreeProvider.setScanPackages(scanPackages);
                providers.add(annotationPathTreeProvider);
            }
        }
        pathRoleMatcher.setPathTreeProviderList(providers);
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultPathRoleMatcher.class)
    public DefaultPathRoleMatcher defaultPathRoleMatcher(){
        return new DefaultPathRoleMatcher();
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(value = FilterRegistrationBean.class)
    @ConditionalOnExpression("'${sureness.support-types}'.contains('com.usthe.sureness.configuration.SupportType.Servlet')")
    public FilterRegistrationBean filterRegistration(
            SecurityManager securityManager
    ) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SurenessFilter(securityManager));
        registration.addUrlPatterns("/*");
        registration.setFilter((Filter)
                applicationContext.getBean("surenessFilter"));
        registration.setName("surenessFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(name = "surenessFilter")
    public Filter surenessFilter(SecurityManager securityManager){
        return new SurenessFilter(securityManager);
    }
}
