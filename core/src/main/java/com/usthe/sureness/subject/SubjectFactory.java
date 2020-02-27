package com.usthe.sureness.subject;

import com.usthe.sureness.processor.exception.UnsupportedTokenException;

/**
 * subject 工厂
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
    SubjectDeclare createSubject(Subject auToken);

    /**
     * description 通过传入的内容对象信息创建对应的token
     * 由于sureness-core想在设计的时候不加其他特定的依赖, 这里就不实现
     * 在httpRequest中获取内容在填充subject了
     * 填充内容源设计成任何内容对象,不绑定框架
     * 基于web的实现在 webFactory中实现它
     * todo subject工厂应该是可以创建多个Subject 就像有多个钥匙 其中有几个都是正确的
     * @param var1 1
     * @return com.usthe.sureness.subject.SubjectAuToken
     * @throws UnsupportedTokenException 无法通过request message创建token时抛出
     */
    Subject createSubjectAuToken(Object var1) throws UnsupportedTokenException;

}
