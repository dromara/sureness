package com.usthe.sureness.subject;

/**
 * subject , token 工厂
 * @author tomsun28
 * @date 00:39 2019-01-24
 */
public interface SubjectFactory {


    /**
     * description 通过 token创建对应的subject
     *
     * @param auToken 1
     * @return com.usthe.sureness.subject.Subject
     */
    Subject createSubject(SubjectAuToken auToken);

    /**
     * description 通过传入的内容对象信息创建对应的token
     *
     * @param var1 1
     * @return com.usthe.sureness.subject.SubjectAuToken
     */
    SubjectAuToken createSubjectAuToken(Object var1);


}
