package com.usthe.sureness.sample.config;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.support.WebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * sureness 配置
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Configuration
public class SurenessConfiguration {

    @Bean
    public SurenessSecurityManager surenessSecurityManager(DefaultPathRoleMatcher pathRoleMatcher,
                                                           ProcessorManager processorManager) {
        SurenessSecurityManager.getInstance().setPathRoleMatcher(pathRoleMatcher);
        SubjectFactory subjectFactory = new WebSubjectFactory();
        SurenessSecurityManager.getInstance().setSubjectFactory(subjectFactory);
        SurenessSecurityManager.getInstance().setProcessorManager(processorManager);
        return SurenessSecurityManager.getInstance();
    }

    @Bean
    public DefaultPathRoleMatcher pathRoleMatcher(PathTreeProvider pathTreeProvider) {
        DefaultPathRoleMatcher.getInstance().setPathTreeProvider(pathTreeProvider);
        DefaultPathRoleMatcher.getInstance().buildTree();
        return DefaultPathRoleMatcher.getInstance();
    }

    @Bean
    public ProcessorManager processorManager(SurenessAccountProvider accountProvider) {
        List<Processor> processorList = new LinkedList<>();
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        return new DefaultProcessorManager(processorList);
    }

}
