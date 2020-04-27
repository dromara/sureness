package com.usthe.sureness.matcher;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.Subject;

/**
 * path - role 匹配 matcher
 * @author tomsun28
 * @date 17:42 2019-03-10
 */
public interface TreePathRoleMatcher {

    /**
     * description : 通过auToken中的 targetUri 在树种匹配出所支持的roles 填充到token中
     * @param auToken 根据接入对象所创建的TOKEN,成功后会将其所需角色塞入TOKEN
     * @throws SurenessNoInitException when matcher not init
     */
    void matchRole(Subject auToken);

    /**
     * 建立起匹配树
     * @throws SurenessNoInitException when matcher not init
     * @throws SurenessLoadDataException when 数据源 not init
     */
    void buildTree();

    /**
     * 重建匹配树
     * @throws SurenessNoInitException when matcher not init
     * @throws SurenessLoadDataException when 数据源 not init
     */
    void rebuildTree();

    /**
     * 通过拥有的排除资源名单判断这个请求所请求的资源是否在排除名单里
     * 资源: requestUri===method
     * @param request 请求内容
     * @return 是排除资源true 否则false
     */
    boolean isExcludedResource(Object request);
}
