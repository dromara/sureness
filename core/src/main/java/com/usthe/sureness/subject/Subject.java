package com.usthe.sureness.subject;


import com.usthe.sureness.subject.support.SurenessSubjectSum;
import com.usthe.sureness.util.ThreadContext;

import java.io.Serializable;
import java.util.List;

/**
 * AuthenticationToken AuthorizationToken 认证鉴权对象
 * @author tomsun28
 * @date 21:58 2019-01-22
 */
public interface Subject extends Serializable {

    /**
     * description 账户名  string
     *
     * @return 账户标识
     */
    Object getPrincipal();

    /**
     * description 认证证书
     *
     * @return 对应账户的认证证书或秘钥
     */
    Object getCredentials();

    /**
     * description 对应账户所拥有的角色
     *
     * @return 角色信息
     */
    Object getOwnRoles();

    /**
     * description 需要访问的资源
     *
     * @return 资源信息
     */
    Object getTargetResource();

    /**
     * description 获取token 在url-role树中匹配出来的roles
     * null表示没有匹配出url，数据集合为0表示匹配出来需要的role为空，即支持所有role
     * 访问 getTargetResource() 所支持的 roles
     *
     * @return 访问此资源所需的角色信息
     */
    Object getSupportRoles();

    /**
     * description 设置所匹配出的role
     *
     * @param var1 所支持角色
     */
    void setSupportRoles(Object var1);

    /**
     * description 通过 自身subject内容创建对应精简内容的subjectSum
     *
     * @return com.usthe.sureness.subject.Subject
     */
    @SuppressWarnings("unchecked")
    default SubjectSum generateSubjectSummary() {
        String principal = (String)getPrincipal();
        List<String> roles = (List<String>)getOwnRoles();
        String targetUri = (String)getTargetResource();
        SubjectSum subject =  SurenessSubjectSum.builder()
                .setTargetResource(targetUri)
                .setRoles(roles)
                .setPrincipal(principal)
                .build();
        // 将subject 绑定到localThread变量中
        ThreadContext.bind(subject);
        // 如果是网关认证中心, 之后可以考虑把subject绑定到request请求中,供子系统使用
        return subject;
    }
}
