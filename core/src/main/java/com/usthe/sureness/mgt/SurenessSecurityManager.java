package com.usthe.sureness.mgt;

import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.processor.exception.BaseSurenessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;


/**
 * 认证鉴权总方法调用默认入口类
 * @author tomsun28
 * @date 15:30 2019-03-03
 */
public class SurenessSecurityManager implements SecurityManager {

    private static final Logger logger = LoggerFactory.getLogger(SurenessSecurityManager.class);

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

    private SurenessSecurityManager() {

    }

    /**
     * description 判断SecurityManager是否初始化完成并且组件加载成功
     *
     * @throws SurenessNoInitException check结果false 抛出异常
     */
    private void checkComponentInit() throws SurenessNoInitException {
        if (subjectFactory == null || pathRoleMatcher == null ||
                processorManager == null) {
            logger.error("SecurityManager init error : SurenessSecurityManager not init fill component");
            throw new SurenessNoInitException("SurenessSecurityManager not init fill component");
        }
    }

    @Override
    public SubjectSum checkIn(Subject token) throws BaseSurenessException {
        checkComponentInit();
        pathRoleMatcher.matchRole(token);
        return processorManager.process(token);
    }

    @Override
    public SubjectSum checkIn(Object var1) throws BaseSurenessException {
        List<Subject> subjectList = createSubject(var1);
        // 对于创建的几个门面钥匙 一把一把试错
        // 若钥匙都不对 抛异常在最后一把 即最后一把试错的结果为展示的错误信息
        Iterator<Subject> subjectIterator = subjectList.iterator();
        BaseSurenessException lastException = new UnsupportedSubjectException("creators not create subject, " +
                "check load noneSubjectCreator");
        while (subjectIterator.hasNext()) {
            Subject thisSubject = subjectIterator.next();
            try {
                return checkIn(thisSubject);
            } catch (BaseSurenessException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    @Override
    public List<Subject> createSubject(Object var1) {
        return subjectFactory.createSubjects(var1);
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

    public SubjectFactory getSubjectFactory() {
        return subjectFactory;
    }

    /**
     * 单例静态内部类
     * @author tomsun28
     * @date 15:30 2019-03-10
     */
    private static class SingleDefaultSecurityManager {
        private static final SurenessSecurityManager INSTANCE = new SurenessSecurityManager();
    }

    public static SurenessSecurityManager getInstance() {
        return SingleDefaultSecurityManager.INSTANCE;
    }
}
