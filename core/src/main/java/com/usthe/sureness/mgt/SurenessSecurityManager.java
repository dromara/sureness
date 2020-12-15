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

import java.util.List;


/**
 * Authentication authorization entrance
 * @author tomsun28
 * @date 15:30 2019-03-03
 */
public class SurenessSecurityManager implements SecurityManager {

    private static final Logger logger = LoggerFactory.getLogger(SurenessSecurityManager.class);

    /**
     *  subject factory
     */
    private SubjectFactory subjectFactory;

    /**
     *  path-role match
     *  match role in pathRoleTree
     */
    private TreePathRoleMatcher pathRoleMatcher;

    /**
     * process manager
     */
    private ProcessorManager processorManager;

    private SurenessSecurityManager() {

    }

    /**
     * Determine whether the SecurityManager is initialized
     * and the component is loaded successfully
     *
     * @throws SurenessNoInitException check false not init
     */
    private void checkComponentInit() {
        if (subjectFactory == null || pathRoleMatcher == null ||
                processorManager == null) {
            logger.error("SecurityManager init error : SurenessSecurityManager not init fill component");
            // The component's own related exceptions or configuration line exceptions are not thrown up
            throw new SurenessNoInitException("SurenessSecurityManager not init fill component");
        }
    }

    @Override
    public SubjectSum checkIn(Subject subject) throws BaseSurenessException {
        // Determine whether the requested resource is a filtered resource
        // if yes, pass directly
        if (pathRoleMatcher.isExcludedResource(subject)) {
            return null;
        }
        pathRoleMatcher.matchRole(subject);
        return processorManager.process(subject);
    }

    @Override
    public SubjectSum checkIn(Object var1) throws BaseSurenessException {
        checkComponentInit();

        // Create a subject list to try auth one by one
        List<Subject> subjectList = createSubject(var1);
        RuntimeException lastException = new UnsupportedSubjectException("this request can not " +
                "create subject by creators");

        // for the subject keys, try one by one
        // if one success, pass and return directly
        for (Subject thisSubject : subjectList) {
            try {
                return checkIn(thisSubject);
            } catch (BaseSurenessException e) {
                lastException = e;
            }
        }
        // if no one success, the throw exception is the lastException
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

    public TreePathRoleMatcher getPathRoleMatcher() {
        return pathRoleMatcher;
    }

    public ProcessorManager getProcessorManager() {
        return processorManager;
    }

    /**
     * singleton
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
