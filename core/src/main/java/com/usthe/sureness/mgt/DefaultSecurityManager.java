package com.usthe.sureness.mgt;

import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 认证鉴权总方法调用默认入口类
 * @author tomsun28
 * @date 15:30 2019-03-03
 */
public class DefaultSecurityManager implements SecurityManager {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSecurityManager.class);

    /**
     *  subject 工厂
     */
    private SubjectFactory subjectFactory;

    /**
     *  path - role 在树中的对应关系匹配接口
     */
    private TreePathRoleMatcher pathRoleMatcher;

    /**
     *  处理器管理者
     */
    private ProcessorManager processorManager;

    private DefaultSecurityManager() {

    }

    /**
     * description 判断SecurityManager是否初始化完成并且组件加载成功
     *
     * @throws SurenessNoInitException check结果false 抛出异常
     */
    private void checkComponentInit() throws SurenessNoInitException{
        if (subjectFactory == null || pathRoleMatcher == null ||
                processorManager == null) {
            LOG.error("SecurityManager init error : DefaultSecurityManager not init fill component");
            throw new SurenessNoInitException("DefaultSecurityManager not init fill component");
        }
    }

    @Override
    public Subject checkIn(SubjectAuToken token) throws SurenessNoInitException {
        checkComponentInit();
        pathRoleMatcher.matchRole(token);
        return processorManager.process(token);
    }

    @Override
    public Subject checkIn(Object var1) throws SurenessNoInitException {
        checkComponentInit();
        SubjectAuToken auToken =  createSubjectAuToken(var1);
        return checkIn(auToken);
    }

    @Override
    public SubjectAuToken createSubjectAuToken(Object var1) throws SurenessNoInitException {
        subjectFactory.checkComponentInit();
        return subjectFactory.createSubjectAuToken(var1);
    }

    @Override
    public Subject createSubject(SubjectAuToken var1) {
        return subjectFactory.createSubject(var1);
    }

    public void setSubjectFactory(SubjectFactory subjectFactory) {
        this.subjectFactory = subjectFactory;
    }

    public void setPathRoleMatcher(TreePathRoleMatcher pathRoleMatcher) {
        this.pathRoleMatcher = pathRoleMatcher;
    }

    public void setProcessorManager(ProcessorManager processorManager) {
        this.processorManager = processorManager;
    }

    /**
     * 单例静态内部类
     * @author tomsun28
     * @date 15:30 2019-03-10
     */
    private static class SingleDefaultSecurityManager {
        private static final DefaultSecurityManager INSTANCE = new DefaultSecurityManager();
    }

    public static DefaultSecurityManager getInstance() {
        return SingleDefaultSecurityManager.INSTANCE;
    }
}
