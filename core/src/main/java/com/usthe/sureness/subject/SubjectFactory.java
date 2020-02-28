package com.usthe.sureness.subject;

import java.util.List;

/**
 * subject 工厂
 * subject工厂可以创建多个Subject 就像有多个钥匙 其中有几个都是正确的
 * 改为注册式 把创建的subject creator 注册到工厂  这样user也可以自定义creator
 * @author tomsun28
 * @date 00:39 2019-01-24
 */
public interface SubjectFactory {

    /**
     * description 通过加载的creators去创建其所支持的subject
     * @param var1 请求对象内容
     * @return com.usthe.sureness.subject.SubjectAuToken return 0 list when can not create subject from var1
     */
    List<Subject> createSubjects(final Object var1);

    /**
     * 向工厂注册subject的creators
     * @param creators subject creator list
     */
    void registerSubjectCreator(List<SubjectCreate> creators);

    /**
     * 加载已经存在于工厂的creators出来使用
     * @return creators
     */
    List<SubjectCreate> loadSubjectCreators();

}
