package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTree;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 默认的path - role 匹配 matcher实现
 * @author tomsun28
 * @date 20:23 2019-03-10
 */
public class DefaultPathRoleMatcher implements TreePathRoleMatcher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPathRoleMatcher.class);

    private static final String LEFT_CON = "[";

    private static final String RIGHT_CON = "]";

    private static final String NULL_ROLE = "[]";

    private static final String EXCLUDE_ROLE = "exclude";

    /** path-role 匹配树存储点 **/
    private final TirePathTree root = new TirePathTree();

    /** path-role 被排除的资源匹配树存储点 **/
    private final TirePathTree excludeRoot = new TirePathTree();

    /** 匹配树数据内容提供者 **/
    private PathTreeProvider pathTreeProvider;

    /** 是否匹配树数据加载完成 **/
    private volatile boolean isTreeInit;

    @Override
    public void matchRole(Subject auToken) {
        if (!isTreeInit) {
            logger.error("DefaultPathRoleMatcher -> root tree is not init");
            throw new SurenessNoInitException("DefaultPathRoleMatcher -> root tree is not init");
        }
        String targetResource = (String) auToken.getTargetResource();
        //[role1,role2,role3], [role1], [], null
        String matchRoleString = root.searchPathFilterRoles(targetResource);
        if (matchRoleString != null && matchRoleString.startsWith(LEFT_CON)
                && matchRoleString.endsWith(RIGHT_CON)) {
            if (NULL_ROLE.equals(matchRoleString)) {
                auToken.setSupportRoles(new ArrayList<>(0));
            } else {
                String[] roles = matchRoleString.substring(1, matchRoleString.length()-1).split(",");
                auToken.setSupportRoles(Arrays.asList(roles));
            }
        }
    }

    @Override
    public void buildTree() {
        isTreeInit = false;
        checkComponentInit();
        clearTree();
        Set<String> resources = pathTreeProvider.providePathData();
        Set<String> excludeResource = pathTreeProvider.provideExcludedResource();

        if (resources != null) {
            resources = resources.stream().map(String::toLowerCase).collect(Collectors.toSet());
            root.buildTree(resources);
        } else {
            logger.error("sureness - pathTreeProvider.providePathData is null, can not load resource");
        }

        if (excludeResource != null) {
            excludeResource = excludeResource.stream()
                    .map(resource -> resource.concat("===").concat(EXCLUDE_ROLE).toLowerCase())
                    .collect(Collectors.toSet());
            excludeRoot.buildTree(excludeResource);
        } else {
            logger.error("sureness - pathTreeProvider.provideExcludedResource is null, can not exclude resource");
        }
        isTreeInit = true;
    }

    @Override
    public void rebuildTree() {
        checkComponentInit();
        Set<String> resources = pathTreeProvider.providePathData();
        Set<String> excludeResource = pathTreeProvider.provideExcludedResource();
        if (resources != null) {
            resources = resources.stream().map(String::toLowerCase).collect(Collectors.toSet());
            root.rebuildTree(resources);
        } else {
            logger.error("sureness - pathTreeProvider.providePathData is null, can not load resource");
        }

        if (excludeResource != null) {
            excludeResource = excludeResource.stream()
                    .map(resource -> resource.concat("===").concat(EXCLUDE_ROLE).toLowerCase())
                    .collect(Collectors.toSet());
            excludeRoot.rebuildTree(excludeResource);
        } else {
            logger.error("sureness - pathTreeProvider.provideExcludedResource is null, can not exclude resource");
        }
    }

    @Override
    public boolean isExcludedResource(Object request) {
        checkComponentInit();
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String requestType = ((HttpServletRequest) request).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        String exclude = excludeRoot.searchPathFilterRoles(targetUri);
        return exclude != null && exclude.equals(EXCLUDE_ROLE);
    }

    private void checkComponentInit() {
        if (pathTreeProvider == null) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher init error : component init not complete");
        }
    }

    private void clearTree() {
        root.clearTree();
    }

    public void setPathTreeProvider(PathTreeProvider pathTreeProvider) {
        this.pathTreeProvider = pathTreeProvider;
    }
}
