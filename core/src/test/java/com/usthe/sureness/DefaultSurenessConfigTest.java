package com.usthe.sureness;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.support.DigestProcessor;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.provider.ducument.DocumentResourceDefaultProvider;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/17 11:02
 */
public class DefaultSurenessConfigTest {

    @Test
    public void init() {
        // resource init
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        DefaultSurenessConfig surenessConfig = new DefaultSurenessConfig();
        DocumentResourceDefaultProvider resourceProvider = new DocumentResourceDefaultProvider();

        // process init
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(resourceProvider);
        processorList.add(passwordProcessor);
        DigestProcessor digestProcessor = new DigestProcessor();
        digestProcessor.setAccountProvider(resourceProvider);
        processorList.add(digestProcessor);
        DefaultProcessorManager processorManager = new DefaultProcessorManager(processorList);


        // pathRoleMatcher init
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProvider(resourceProvider);
        pathRoleMatcher.buildTree();


        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = Arrays.asList(
                new NoneSubjectServletCreator(),
                new DigestSubjectServletCreator(),
                new BasicSubjectServletCreator(),
                new JwtSubjectServletCreator());
        subjectFactory.registerSubjectCreator(subjectCreates);

        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
    }
}