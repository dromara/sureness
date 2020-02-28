package com.usthe.sureness.subject;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * subject create factory
 * @author tomsun28
 * @date 23:35 2019-05-12
 */
public class SurenessSubjectFactory implements SubjectFactory {

    /**
     * subject creator list
     */
    private List<SubjectCreate> subjectCreators;

    @Override
    public List<Subject> createSubjects(final Object request) {
        return loadSubjectCreators()
                .stream()
                .filter(creator -> creator.canSupportSubject(request))
                .map(creator -> creator.createSubject(request))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void registerSubjectCreator(List<SubjectCreate> creator) {
        if (subjectCreators == null) {
            subjectCreators = new LinkedList<>();
        }
        subjectCreators.addAll(creator);
    }

    @Override
    public List<SubjectCreate> loadSubjectCreators() {
        if (subjectCreators == null) {
            throw new RuntimeException("subjectFactory not init, not have subjectCreator");
        }
        return subjectCreators;
    }
}
