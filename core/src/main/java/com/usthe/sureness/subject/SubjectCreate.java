package com.usthe.sureness.subject;

/**
 * 不同类型的subject创建
 * @author tomsun28
 * @date 23:44 2020-02-27
 */
public interface SubjectCreate {


    /**
     * 通过获取context简明信息，初步判断是否能支持这种类型的subject创建
     * 初步判断，原则是：特征通过则true
     * @param context 请求主体内容
     * @return 判断能创建此类型true 否则false
     */
    boolean canSupportSubject(Object context);

    /**
     * 通过context获取信息创建对应类型的subject
     * @param context 请求主体内容
     * @return subject return null when can not create by context
     */
    Subject createSubject(Object context);
}
