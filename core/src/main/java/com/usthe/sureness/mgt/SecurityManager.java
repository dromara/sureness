package com.usthe.sureness.mgt;

import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.processor.exception.BaseSurenessException;

import java.util.List;

/**
 * 认证鉴权总方法调用入口接口
 * @author tomsun28
 * @date 22:33 2019-01-23
 */
public interface SecurityManager {


    /**
     * description 通过不同类型的token进去到认证授权流程中
     * 认证鉴权总入口,  不仅仅是login 还有鉴权
     * @param token token
     * @return com.usthe.sureness.subject.Subject
     * @throws BaseSurenessException 抛出不同的异常
     */
    SubjectSum checkIn(Subject token) throws BaseSurenessException;

    /**
     * description checkin 进入认证鉴权入口
     *
     * @param var1 请求内容对象
     * @return com.usthe.sureness.subject.Subject
     * return null when the request uri===method is in excluded resource
     * @throws BaseSurenessException 抛出不同的认证鉴权异常
     */
    SubjectSum checkIn(Object var1) throws BaseSurenessException;

    /**
     * description 传入请求信息,信息里有对应的请求认证消息，请求资源路径等 eg: httpRequest
     * 根据请求信息获取建立对应类型的token
     *
     * @param var1 内容对象
     * @return com.usthe.sureness.subject.SubjectAuToken
     * @throws UnsupportedSubjectException 不支持的TOKEN时抛出异常
     */
     List<Subject> createSubject(Object var1) throws UnsupportedSubjectException;

}
