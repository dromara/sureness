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
     * description 通过传入的内容对象信息创建对应的token
     * 由于sureness-core想在设计的时候不加其他特定的依赖, 这里就不实现
     * 在httpRequest中获取内容在填充subject了
     * 填充内容源设计成任何内容对象,不绑定框架
     * @param var1 请求对象内容
     * @return com.usthe.sureness.subject.SubjectAuToken return 0 list when can not create subject from var1
     */
    List<Subject> createSubjects(final Object var1);

    /**
     * 向工厂注册subject的creator
     * @param creator subject creator
     */
    void registerSubjectCreator(SubjectCreate creator);

    /**
     * 加载已经存在于工厂的creators出来使用
     * @return creators
     */
    List<SubjectCreate> loadSubjectCreators();

}
