package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTreeUtil;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author tomsun28
 * @date 20:23 2019-03-10
 */
public class DefaultPathRoleMatcher implements TreePathRoleMatcher {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPathRoleMatcher.class);

    /**
     *  path-role 匹配树存储点
     */
    private final TirePathTreeUtil.Node root = new TirePathTreeUtil.Node("root");

    /**
     * 匹配树数据内容提供者
     */
    private PathTreeProvider pathTreeProvider;

    /**
     * 是否匹配树数据加载完成
     */
    private boolean isTreeInit;

    private DefaultPathRoleMatcher() {

    }

    @Override
    public void matchRole(SubjectAuToken auToken) throws SurenessNoInitException {
        if (!isTreeInit) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher -> root tree is not init");
        }
        String targetResource = (String) auToken.getTargetResource();
        //[role1,role2,role3], [role1], null
        String matchRoleString = TirePathTreeUtil.searchPathFilterRoles(targetResource, root);
        if (matchRoleString == null || "".equals(matchRoleString) ||
                matchRoleString.length() <= 2) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher -> matchRoleString error");
        }
        String[] roles = matchRoleString.substring(1, matchRoleString.length()-2).split(",");
        List<String> roleList = new LinkedList<>(Arrays.asList(roles));
        auToken.setSupportRoles(roleList);
    }

    private void checkComponentInit() throws SurenessNoInitException{
        if (pathTreeProvider == null) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher init error : component init not complete");
        }

    }

    public void setPathTreeProvider(PathTreeProvider pathTreeProvider) {
        this.pathTreeProvider = pathTreeProvider;
    }

    public void buildTree() throws SurenessNoInitException{
        checkComponentInit();
        Set<String> resources = pathTreeProvider.providePathData();
        TirePathTreeUtil.buildTree(resources, root);
        isTreeInit = true;
    }

    public void rebuildTree() {
        clearTree();
        isTreeInit = false;
        buildTree();
    }

    public void clearTree() {
        TirePathTreeUtil.clearTree(root);
    }

    /**
     * 内部单例静态类
     * @author tomsun28
     * @date 20:50 2019-03-10
     */
    private static class SingleDefaultPathRoleMatcher {
        private static final DefaultPathRoleMatcher INSTANCE = new DefaultPathRoleMatcher();
    }

    public static DefaultPathRoleMatcher getInstance() {
        return SingleDefaultPathRoleMatcher.INSTANCE;
    }

}
