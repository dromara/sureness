package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTreeUtil;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 是否数据加载完成
     */
    private boolean isInit;

    private DefaultPathRoleMatcher() {

    }

    @Override
    public void matchRole(SubjectAuToken auToken) {

    }

    private void checkComponentInit() throws SurenessNoInitException{
        if (!isInit || pathTreeProvider == null) {
            throw new SurenessNoInitException("DefaultPathRoleMatcher init error : component init not complete");
        }

    }

    public void setPathTreeProvider(PathTreeProvider pathTreeProvider) {
        this.pathTreeProvider = pathTreeProvider;
    }

    public void buildTree() throws SurenessNoInitException{
        checkComponentInit();
        Set<String> resources = pathTreeProvider.providePathData();


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
