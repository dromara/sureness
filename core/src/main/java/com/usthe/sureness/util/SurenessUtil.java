package com.usthe.sureness.util;

import com.usthe.sureness.subject.SubjectAuToken;

/**
 * 一些处理工具类
 * @author tomsun28
 * @date 19:07 2019-03-09
 */
public abstract class SurenessUtil {

    /**
     * description 通过传入的Object来创建具有基本信息的token
     * 基本信息： url===method
     * 此方法在web模块实现
     * @param var1 1
     * @return com.usthe.sureness.subject.SubjectAuToken
     */
    public abstract SubjectAuToken createSubjectAuToken(Object var1);


}
