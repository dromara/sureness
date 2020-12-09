package com.usthe.sureness.subject;

import java.util.List;

/**
 * subject factory, can create multi subject by subject creators
 * the factory is registered, the subject creators register them in this factory
 * @author tomsun28
 * @date 00:39 2019-01-24
 */
public interface SubjectFactory {

    /**
     * Create the subject supported by the loaded creators
     * @param var1 request body
     * @return com.usthe.sureness.subject.Subject return 0 list when can not create subject from var1
     */
    List<Subject> createSubjects(final Object var1);

    /**
     * register subject creator to factory
     * @param creators subject creator list
     */
    void registerSubjectCreator(List<SubjectCreate> creators);

    /**
     * load the subject creators
     * @return creators
     */
    List<SubjectCreate> loadSubjectCreators();

}
