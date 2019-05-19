package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTreeUtil;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * 默认的path - role 匹配 matcher实现
 * @author tomsun28
 * @date 20:23 2019-03-10
 */
public class DefaultPathRoleMatcher implements TreePathRoleMatcher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPathRoleMatcher.class);

    private static final String LEFT_CON = "[";

    private static final String RIGHT_CON = "]";

    /** path-role 匹配树存储点 **/
    private final TirePathTreeUtil.Node root = new TirePathTreeUtil.Node("root");

    /** 匹配树数据内容提供者 **/
    private PathTreeProvider pathTreeProvider;

    /** 是否匹配树数据加载完成 **/
    private boolean isTreeInit;

    private DefaultPathRoleMatcher() {}

    public static DefaultPathRoleMatcher getInstance() {
        return SingleDefaultPathRoleMatcher.INSTANCE;
    }

    @Override
    public void matchRole(SubjectAuToken auToken) throws SurenessNoInitException {
        if (!isTreeInit) {
            logger.error("DefaultPathRoleMatcher -> root tree is not init");
            throw new SurenessNoInitException("DefaultPathRoleMatcher -> root tree is not init");
        }
        String targetResource = (String) auToken.getTargetResource();
        //[role1,role2,role3], [role1], null
        String matchRoleString = TirePathTreeUtil.searchPathFilterRoles(targetResource, root);
        if (matchRoleString != null && matchRoleString.startsWith(LEFT_CON)
                && matchRoleString.endsWith(RIGHT_CON)
                && matchRoleString.length() != 2) {
            String[] roles = matchRoleString.substring(1, matchRoleString.length()-1).split(",");
            auToken.setSupportRoles(Arrays.asList(roles));
        }
    }

    @Override
    public void buildTree() throws SurenessNoInitException, SurenessLoadDataException {
        isTreeInit = false;
        checkComponentInit();
        clearTree();
        Set<String> resources = pathTreeProvider.providePathData();
        TirePathTreeUtil.buildTree(resources, root);
        isTreeInit = true;
    }

    private void checkComponentInit() throws SurenessNoInitException{
        if (pathTreeProvider == null) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher init error : component init not complete");
        }
    }

    private void clearTree() {
        TirePathTreeUtil.clearTree(root);
    }

    public void setPathTreeProvider(PathTreeProvider pathTreeProvider) {
        this.pathTreeProvider = pathTreeProvider;
    }
    /**
     * 内部单例静态类
     * @author tomsun28
     * @date 20:50 2019-03-10
     */
    private static class SingleDefaultPathRoleMatcher {
        private static final DefaultPathRoleMatcher INSTANCE = new DefaultPathRoleMatcher();
    }
}
