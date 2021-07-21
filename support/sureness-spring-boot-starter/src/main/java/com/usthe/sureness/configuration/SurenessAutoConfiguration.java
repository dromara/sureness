package com.usthe.sureness.configuration;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.mgt.SecurityManager;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.provider.annotation.AnnotationPathTreeProvider;
import com.usthe.sureness.provider.ducument.DocumentAccountProvider;
import com.usthe.sureness.provider.ducument.DocumentPathTreeProvider;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.BasicSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.BasicSubjectServletCreator;
import com.usthe.sureness.subject.creater.DigestSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.DigestSubjectServletCreator;
import com.usthe.sureness.subject.creater.JwtSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.JwtSubjectServletCreator;
import com.usthe.sureness.subject.creater.JwtSubjectWsJaxRsCreator;
import com.usthe.sureness.subject.creater.JwtSubjectWsServletCreator;
import com.usthe.sureness.subject.creater.NoneSubjectJaxRsCreator;
import com.usthe.sureness.subject.creater.NoneSubjectServletCreator;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.usthe.sureness.DefaultSurenessConfig.SUPPORT_JAX_RS;


/**
 * @author wangtao
 * @date 2021/7/3
 */
@Configuration
@ConditionalOnProperty(prefix = "sureness", name = "enabled", havingValue = "true",
        matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@AutoConfigureAfter(value = {SurenessProperties.class})
public class SurenessAutoConfiguration implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurenessAutoConfiguration.class);

    private ApplicationContext applicationContext;

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
    SubjectFactory subjectFactory(SurenessProperties surenessProperties) {
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = new ArrayList<>();
        Set<String> sets = new HashSet<>();
        if (!CollectionUtils.isEmpty(surenessProperties.getAuthTypes())){
            sets = surenessProperties.getAuthTypes();
        }
        if (SUPPORT_JAX_RS.equals(surenessProperties.getSupportType())) {
            subjectCreates.add(new NoneSubjectJaxRsCreator());
            if (sets.contains(SurenessConstant.BASIC)){
                subjectCreates.add(new BasicSubjectJaxRsCreator());
            }
            if (sets.contains(SurenessConstant.JWT)){
                subjectCreates.add(new JwtSubjectJaxRsCreator());
                subjectCreates.add(new JwtSubjectWsJaxRsCreator());
            }
            if (sets.contains(SurenessConstant.DIGEST)){
                subjectCreates.add(new DigestSubjectJaxRsCreator());
            }
        } else {
            subjectCreates.add(new NoneSubjectServletCreator());

            if (sets.contains(SurenessConstant.BASIC)){
                subjectCreates.add(new BasicSubjectServletCreator());
            }
            if (sets.contains(SurenessConstant.JWT)){
                subjectCreates.add(new JwtSubjectServletCreator());
                subjectCreates.add(new JwtSubjectWsServletCreator());
            }
            if (sets.contains(SurenessConstant.DIGEST)){
                subjectCreates.add(new DigestSubjectServletCreator());
            }
        }
        subjectFactory.registerSubjectCreator(subjectCreates);
        LOGGER.info("SurenessSubjectFactory init");
        return subjectFactory;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityManager.class)
    SecurityManager securityManager(
            SurenessProperties surenessProperties,
            ProcessorManager processorManager,
            TreePathRoleMatcher pathRoleMatcher,
            SubjectFactory subjectFactory) {
        if (!CollectionUtils.isEmpty(surenessProperties.getAuthTypes()) &&
                surenessProperties.getSupportType().contains(SurenessConstant.JWT)){
            JsonWebTokenUtil.setDefaultSecretKey(surenessProperties.getToken());
        }
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        return securityManager;
    }


    @Bean
    @ConditionalOnMissingBean(ProcessorManager.class)
    ProcessorManager processorManager(SurenessAccountProvider accountProvider,
                                      SurenessProperties surenessProperties) {
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        if (!CollectionUtils.isEmpty(surenessProperties.getAuthTypes()) &&
                surenessProperties.getSupportType().contains(SurenessConstant.JWT)){
            JwtProcessor jwtProcessor = new JwtProcessor();
            processorList.add(jwtProcessor);
        }
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        return new DefaultProcessorManager(processorList);
    }

    @Bean
    @ConditionalOnMissingBean(TreePathRoleMatcher.class)
    TreePathRoleMatcher pathRoleMatcher(PathTreeProvider pathTreeProvider,
                                        DefaultPathRoleMatcher pathRoleMatcher,
                                        SurenessProperties surenessProperties) {
        List<PathTreeProvider> tmp = new ArrayList<>();
        tmp.add(pathTreeProvider);
        if (!CollectionUtils.isEmpty(surenessProperties.getScanPackages())) {
            AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
            annotationPathTreeProvider.setScanPackages(surenessProperties.getScanPackages());
            tmp.add(annotationPathTreeProvider);
        }
        pathRoleMatcher.setPathTreeProviderList(tmp);
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultPathRoleMatcher.class)
    public   DefaultPathRoleMatcher defaultPathRoleMatcher(){
        return  new DefaultPathRoleMatcher();
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(value = FilterRegistrationBean.class, name = "surenessFilter")
    @ConditionalOnProperty(name="sureness.supportType",havingValue = "servlet")
    public FilterRegistrationBean testFilterRegistration(
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


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
