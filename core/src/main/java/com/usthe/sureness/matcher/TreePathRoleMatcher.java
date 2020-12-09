package com.usthe.sureness.matcher;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.Subject;

/**
 * path-role  matcher interface
 * @author tomsun28
 * @date 17:42 2019-03-10
 */
public interface TreePathRoleMatcher {

    /**
     * Use the targetUri in the subject to match the supported roles in the tree and fill in the subject
     * @param subject After success, the required role will be inserted into the subject
     * @throws SurenessNoInitException when matcher not init
     */
    void matchRole(Subject subject);

    /**
     * build the pathRole match tree
     * @throws SurenessNoInitException when matcher not init
     * @throws SurenessLoadDataException when datasource not init
     */
    void buildTree();

    /**
     * rebuild the pathRole match tree
     * @throws SurenessNoInitException when matcher not init
     * @throws SurenessLoadDataException when datasource not init
     */
    void rebuildTree();

    /**
     * Determine whether the resource requested by this request is in the exclusion list
     * resource: requestUri===method
     * @param request request
     * @return in the exclusion list return true, else false
     */
    boolean isExcludedResource(Subject request);
}
