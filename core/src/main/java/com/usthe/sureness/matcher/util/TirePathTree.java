package com.usthe.sureness.matcher.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 字典匹配树  支持 * **, 暂不支持?
 * 为什么不支持ant的?匹配, 目前的算法加入?效率不高, ?在sureness这种过滤链使用场景不高,完全可以其他取代,没有必要为了支持而支持
 * 匹配优先级: 原始字符串 大于 * 大于 **
 * @author tomsun28
 * @date 19:25 2019-01-18
 */
public class TirePathTree {

    private static final Logger logger = LoggerFactory.getLogger(TirePathTree.class);

    private static final String NODE_TYPE_PATH_NODE = "pathNode";
    private static final String NODE_TYPE_MAY_PATH_END = "mayPathEnd";
    private static final String NODE_TYPE_METHOD = "methodNode";
    private static final String NODE_TYPE_FILTER_ROLES = "rolesNode";
    private static final String URL_PATH_SPLIT = "/";
    private static final String MATCH_ONE = "*";
    private static final String MATCH_ALL = "**";
    private static final int PATH_NODE_NUM_3 = 3;
    private static final int PATH_NODE_NUM_2 = 2;
    private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("/+");

    /**
     * 根节点
     */
    private volatile Node root;

    public TirePathTree() {
        this.root = new Node("root");
    }

    /**
     * 新建字典匹配树
     * @param paths 资源路径
     */
    public synchronized void buildTree(Set<String> paths) {
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - start buildTree...");
        }
        clearTree();
        for (String path : paths) {
            insertNode(path, this.root);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - buildTree finish");
        }
    }

    /**
     * 重建字典匹配树，更新字典树数据
     * 保证重建时不影响读，并发方式RCU -- read copy update
     * @param paths paths 资源
     */
    public synchronized void rebuildTree(Set<String> paths) {
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - start rebuildTree..., try rcu current way");
        }
        Node buildRoot = new Node("root");
        for (String path : paths) {
            insertNode(path, buildRoot);
        }
        this.root = buildRoot;
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - rebuildTree finish");
        }
    }

    /**
     * 清空字典树
     */
    public void clearTree() {
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - clearTree");
        }
        root.getChildren().clear();
    }

    /**
     * 获取当前匹配树存在的匹配资源(URL+METHOD)数量
     * @return int 资源数量
     */
    public int getResourceNum() {
        int resourceNum = 0;
        // 广度层级遍历
        Queue<Node> resourceList = new LinkedList<>();
        resourceList.add(root);
        while (!resourceList.isEmpty()) {
            Node currentNode = resourceList.poll();
            if (NODE_TYPE_METHOD.equals(currentNode.nodeType)) {
                resourceNum ++;
            }
            if (currentNode.getChildren() != null && !currentNode.getChildren().isEmpty()) {
                resourceList.addAll(currentNode.getChildren().values());
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("sureness - current PathTree resource num is: {}", resourceNum);
        }
        return resourceNum;
    }

    /**
     * 根据path从树里匹配该路径需要的 [role2,role3,role4]
     * @param path   /api/v2/host/detail===get
     * @return java.lang.String [role1,role2]
     */
    public String searchPathFilterRoles(String path) {
        if (path == null || "".equals(path) || !path.startsWith(URL_PATH_SPLIT)) {
            return null;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("sureness - searchPathFilterRoles, path is {}", path);
        }
        path = PATH_SPLIT_PATTERN.matcher(path).replaceAll("/");
        path = path.substring(1).toLowerCase();
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_2) {
            return null;
        }
        String[] urlPac = tmp[0].split("/");
        String method = tmp[1];

        // 模式匹配   * **
        Node current = root;
        String matchRole;
        if (current.getChildren().containsKey(urlPac[0])) {
            matchRole = searchPathRole(current.getChildren().get(urlPac[0]), urlPac, 0, method);
            if (matchRole != null) {
                return matchRole;
            }
        }
        if (current.getChildren().containsKey(MATCH_ONE)) {
            matchRole = searchPathRole(current.getChildren().get(MATCH_ONE), urlPac, 0, method);
            if (matchRole != null) {
                return matchRole;
            }
        }
        if (current.getChildren().containsKey(MATCH_ALL)) {
            Node matchAllNode = current.getChildren().get(MATCH_ALL);
            if (NODE_TYPE_MAY_PATH_END.equals(matchAllNode.getNodeType())) {
                Node methodNode = matchAllNode.getChildren().get(method);
                if (methodNode != null && NODE_TYPE_METHOD.equals(methodNode.getNodeType())) {
                    return methodNode.getChildren().keySet().iterator().next();
                }
            } else {
                for (String key : matchAllNode.getChildren().keySet()) {
                    int flow = 1;
                    while (flow < urlPac.length) {
                        if (key.equals(urlPac[flow])) {
                            matchRole = searchPathRole(matchAllNode.getChildren().get(key), urlPac, flow, method);
                            if (matchRole != null) {
                                return matchRole;
                            }
                        }
                        flow ++;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 从当前node匹配查找对应分支的叶子节点
     * @param current 当前node
     * @param urlPac urlPath字符串组
     * @param currentFlow 当前第一个字符串
     * @param method http请求方法
     * @return 匹配到返回[role,role2] 匹配不到返回null
     */
    private String searchPathRole(Node current, String[] urlPac, int currentFlow, String method) {
        if (current == null || urlPac == null || currentFlow >= urlPac.length
                || currentFlow < 0 || method == null || "".equals(method)) {
            return null;
        }

        // fast fail
        if (isNoMatchString(current.getData(), urlPac[currentFlow])) {
            return null;
        }
        if (currentFlow == urlPac.length - 1) {
            if (NODE_TYPE_MAY_PATH_END.equals(current.getNodeType())) {
                Node methodNode = current.getChildren().get(method);
                if (methodNode != null && NODE_TYPE_METHOD.equals(methodNode.getNodeType())) {
                    return methodNode.getChildren().keySet().iterator().next();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        String matchRole;
        String nextUrlPac = urlPac[currentFlow + 1];
        if (current.getChildren().containsKey(nextUrlPac)) {
            matchRole = searchPathRole(current.getChildren().get(nextUrlPac), urlPac, currentFlow + 1, method);
            if (matchRole != null) {
                return matchRole;
            }
        }
        if (current.getChildren().containsKey(MATCH_ONE)) {
            matchRole = searchPathRole(current.getChildren().get(MATCH_ONE), urlPac, currentFlow + 1, method);
            if (matchRole != null) {
                return matchRole;
            }
        }
        if (current.getChildren().containsKey(MATCH_ALL)) {
            Node matchAllNode = current.getChildren().get(MATCH_ALL);
            if (NODE_TYPE_MAY_PATH_END.equals(matchAllNode.getNodeType())) {
                Node methodNode = matchAllNode.getChildren().get(method);
                if (methodNode != null && NODE_TYPE_METHOD.equals(methodNode.getNodeType())) {
                    return methodNode.getChildren().keySet().iterator().next();
                }
            } else {
                for (String key : matchAllNode.getChildren().keySet()) {
                    int flow = currentFlow;
                    while (flow < urlPac.length) {
                        if (key.equals(urlPac[flow])) {
                            matchRole = searchPathRole(matchAllNode.getChildren().get(key), urlPac, flow, method);
                            if (matchRole != null) {
                                return matchRole;
                            }
                        }
                        flow ++;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 判断 pattern是否不匹配pathNode
     * @param pattern 匹配串 * **
     * @param pathNode 被匹配串
     * @return 匹配失败 true 成功 false
     */
    private boolean isNoMatchString(String pattern, String pathNode) {
        if (pattern == null && pathNode == null) {
            return false;
        }
        if (pattern == null || pathNode == null) {
            return true;
        }
        return !(pattern.equals(pathNode) || MATCH_ONE.equals(pattern)
                || MATCH_ALL.equals(pattern));
    }

    /**
     * description 插入节点
     * @param path path = /api/v1/host/detail===GET===[role2,role3,role4]
     */
    private void insertNode(String path, Node rootNode) {
        if (path == null || "".equals(path) || !path.startsWith(URL_PATH_SPLIT)) {
            return;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("sureness - begin insertNode, path is {}", path);
        }
        path = PATH_SPLIT_PATTERN.matcher(path).replaceAll("/");
        // 去除第一个 /
        path = path.substring(1).toLowerCase();
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_3) {
            return;
        }
        String[] urlPac = tmp[0].split(URL_PATH_SPLIT);
        String method = tmp[1];
        String supportRoles = tmp[2];
        Node current = rootNode;
        // 开始插入URL节点
        for (String urlData : urlPac) {
            if (!current.getChildren().containsKey(urlData)) {
                current.insertChild(urlData);
            }
            current = current.getChildren().get(urlData);
        }
        // 设置NODE_TYPE_MAY_PATH_END节点类型
        current.setNodeType(NODE_TYPE_MAY_PATH_END);
        // 开始插入httpMethod节点,如果已经存在，则不覆盖修改原来配置
        if (!current.getChildren().containsKey(method)) {
            current.insertChild(method, NODE_TYPE_METHOD);
        }
        current = current.getChildren().get(method);
        // 开始插入叶子节点 supportRoles
        // 每条资源只能对应一 supportRoles ,httpMethod下最多一个孩子节点
        // 如果已经存在，则不覆盖修改原来配置
        if (current.getChildren().isEmpty()) {
            current.insertChild(supportRoles, NODE_TYPE_FILTER_ROLES);
        }
    }

    /**
     * 树节点类
     */
    private static class Node {

        /** 当前节点的类型 **/
        private String nodeType;

        /** 节点对应的数据 **/
        private String data;

        /** 孩子节点 **/
        private Map<String, Node> children;

        private Node(String data, String nodeType) {
            this.data = data;
            this.nodeType = nodeType;
            this.children = new HashMap<>();
        }
        private Node(String data) {
            this.data = data;
            this.nodeType = NODE_TYPE_PATH_NODE;
            this.children = new HashMap<>();
        }

        private void insertChild(String data) {
            this.children.put(data, new Node(data));
        }

        private void insertChild(String data,String nodeType) {
            this.children.put(data, new Node(data, nodeType));
        }

        private String getNodeType() {
            return nodeType;
        }

        private void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        private String getData() {
            return data;
        }

        private void setData(String data) {
            this.data = data;
        }

        private Map<String, Node> getChildren() {
            return children;
        }

        private void setChildren(Map<String, Node> children) {
            this.children = children;
        }

    }
}
