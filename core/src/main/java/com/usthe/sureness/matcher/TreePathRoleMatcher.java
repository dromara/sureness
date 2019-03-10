package com.usthe.sureness.matcher;

import com.usthe.sureness.subject.SubjectAuToken;

/**
 * path - role 匹配 matcher
 * @author tomsun28
 * @date 17:42 2019-03-10
 */
public interface TreePathRoleMatcher {

    /**
     * description 通过targetUri 在树种匹配出所支持的roles
     *
     */

    /**
     * description : 通过auToken中的 targetUri 在树种匹配出所支持的roles 填充到token中
     * TODO 抛出定制的异常
     * @param auToken 1
     */
    void matchRole(SubjectAuToken auToken);

}
