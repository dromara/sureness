package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTree;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

import java.util.stream.Collectors;

/**
 * default path - role matcher
 * @author tomsun28
 * @date 20:23 2019-03-10
 */
public class DefaultPathRoleMatcher implements TreePathRoleMatcher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPathRoleMatcher.class);

    private static final String LEFT_CON = "[";

    private static final String RIGHT_CON = "]";

    private static final String NULL_ROLE = "[]";

    private static final String EXCLUDE_ROLE = "exclude";

    /** path-role, match tree storage **/
    private final TirePathTree root = new TirePathTree();

    /** exclude path-role, match tree storage **/
    private final TirePathTree excludeRoot = new TirePathTree();

    /** Match tree data content provider list **/
    private List<PathTreeProvider> pathTreeProviderList;

    /** Whether the matching tree data has been loaded **/
    private volatile boolean isTreeInit;

    @Override
    public void matchRole(Subject subject) {
        if (!isTreeInit) {
            logger.error("DefaultPathRoleMatcher -> root tree is not init");
            throw new SurenessNoInitException("DefaultPathRoleMatcher -> root tree is not init");
        }
        String targetResource = (String) subject.getTargetResource();
        //[role1,role2,role3], [role1], [], null
        String matchRoleString = root.searchPathFilterRoles(targetResource);
        if (nonNull(matchRoleString) && matchRoleString.startsWith(LEFT_CON)
                && matchRoleString.endsWith(RIGHT_CON)) {
            if (NULL_ROLE.equals(matchRoleString)) {
                subject.setSupportRoles(new ArrayList<>(0));
            } else {
                String[] roles = matchRoleString.substring(1, matchRoleString.length()-1).split(",");
                subject.setSupportRoles(Arrays.asList(roles));
            }
        }
    }

    @Override
    public void buildTree() {
        isTreeInit = false;
        checkComponentInit();
        Set<String> resources = new HashSet<>();
        Set<String> excludeResources = new HashSet<>();
        iterateOverPathTreeProviderList(resources, excludeResources);
        root.buildTree(resources);

        excludeResources = excludeResources.stream()
                .map(resource -> resource.concat("===").concat(EXCLUDE_ROLE).toLowerCase())
                .collect(Collectors.toSet());
        excludeRoot.buildTree(excludeResources);
        isTreeInit = true;
    }

    @Override
    public void rebuildTree() {
        checkComponentInit();
        Set<String> resources = new HashSet<>();
        Set<String> excludeResources = new HashSet<>();
        iterateOverPathTreeProviderList(resources, excludeResources);
        root.rebuildTree(resources);

        excludeResources = excludeResources.stream()
                .map(resource -> resource.concat("===").concat(EXCLUDE_ROLE).toLowerCase())
                .collect(Collectors.toSet());
        excludeRoot.rebuildTree(excludeResources);
    }

    @Override
    public boolean isExcludedResource(Subject request) {
        checkComponentInit();
        String exclude = excludeRoot.searchPathFilterRoles((String) request.getTargetResource());
        return nonNull(exclude) && exclude.equals(EXCLUDE_ROLE);
    }

    private void checkComponentInit() {
        if (isNull(pathTreeProviderList)) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher init error : component init not complete");
        }
    }
    
    private void clearTree() {
        root.clearTree();
        excludeRoot.clearTree();
    }

    public void setPathTreeProvider(PathTreeProvider pathTreeProvider) {
        if (isNull(pathTreeProviderList)) {
            pathTreeProviderList = new LinkedList<>();
        }
        pathTreeProviderList.add(pathTreeProvider);
    }

    public void setPathTreeProviderList(List<PathTreeProvider> providerList) {
        pathTreeProviderList = providerList;
    }
    public DefaultPathRoleMatcher addPathTreeProvider(PathTreeProvider pathTreeProvider) {
        if (pathTreeProviderList == null) {
            pathTreeProviderList = new LinkedList<>();
        }
        pathTreeProviderList.add(pathTreeProvider);
        return this;
    }


    private void iterateOverPathTreeProviderList(Set<String> resources, Set<String> excludeResources) {
        for (PathTreeProvider provider : pathTreeProviderList) {
            Set<String> resourceTmp = provider.providePathData();
            Set<String> excludeResourceTmp = provider.provideExcludedResource();
            if (nonNull(resourceTmp)) {
                resources.addAll(resourceTmp);
            } else {
                logger.warn("sureness - pathTreeProvider: {} providePathData is null", provider);
            }
            if (nonNull(excludeResourceTmp)) {
                excludeResources.addAll(excludeResourceTmp);
            } else {
                logger.warn("sureness - pathTreeProvider: {} provideExcludedResource is null", provider);
            }
        }
    }
}
