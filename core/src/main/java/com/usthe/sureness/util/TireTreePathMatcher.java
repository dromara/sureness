package com.usthe.sureness.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author tomsun28
 * @date 19:25 2019-01-18
 */
public class TireTreePathMatcher {

    private static final String NODE_TYPE_PATH_NODE = "pathNode";
    private static final String NODE_TYPE_PATH_END = "isPathEnd";
    private static final String NODE_TYPE_METHOD = "methodNode";
    private static final String NODE_TYPE_FILTER_ROLES = "filterRolesNode";

    private static final int PATH_NODE_NUM_3 = 3;
    private static final int PATH_NODE_NUM_2 = 2;


    private final Node root = new Node("root");

    private TireTreePathMatcher() {

    }

    public static synchronized TireTreePathMatcher getInstance() {
        return PathTireTreeHandler.instance;
    }

    /**
     * description 插入节点
     *
     * @param path path = /api/v1/host/detail===GET===jwt[role2,role3,role4]
     */
    private void insertNode(String path) {
        if (path == null || "".equals(path)) {
            return;
        }
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_3) {
            return;
        }
        String[] urlPac = tmp[0].split("/");
        String method = tmp[1];
        String filterRoles = tmp[2];
        Node current = this.root;
        //开始插入URL节点
        for (String urlData : urlPac) {
            if (!current.getChildren().containsKey(urlData.toUpperCase())) {
                current.insertChild(urlData.toUpperCase());
            }
            current = current.getChildren().get(urlData.toUpperCase());
        }
        current.setNodeType(NODE_TYPE_PATH_END);
        //开始插入httpMethod节点
        if (!current.getChildren().containsKey(method.toUpperCase())) {
            current.insertChild(method.toUpperCase(), NODE_TYPE_METHOD);
        }
        current = current.getChildren().get(method.toUpperCase());
        //开始插入filterRoles节点
        //每条资源只能对应一种filter,httpMethod下最多一个孩子节点
        if (current.getChildren().isEmpty()) {
            current.insertChild(filterRoles.toUpperCase(), NODE_TYPE_FILTER_ROLES);
        }

    }

    /**
     *  新建字典匹配树
     * @param paths 1
     */
    public void buildTree(Set<String> paths) {
        for (String path : paths) {
            insertNode(path);
        }
    }

    /**
     *  重建字典匹配树
     * @param paths 1
     */
    public synchronized void reBuildTree(Set<String> paths) {
        destoryTree();
        for (String path : paths) {
            insertNode(path);
        }
    }

    /**
     * 清空字典树
     */
    private void destoryTree() {
        this.root.getChildren().clear();
    }

    /**
     * 根据path从树里匹配该路径需要的 filter[role2,role3,role4]
     * @param path   /api/v2/host/detail===GET
     * @return java.lang.String
     */
    public String searchPathFilterRoles(String path) {
        if (path == null || "".equals(path)) {
            return null;
        }
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_2) {
            return null;
        }
        String[] urlPac = tmp[0].split("/");
        String method = tmp[1];
        Node current = this.root;
        //支持基于ant的模式匹配
        for (String data : urlPac) {
            if (current.getChildren().containsKey(data.toUpperCase())) {
                current = current.getChildren().get(data.toUpperCase());
            } else if (current.getChildren().containsKey("*")) {
                // * 代表匹配一个data节点
                current = current.getChildren().get("*");
            } else if (current.getChildren().containsKey("**")) {
                // ** 代表匹配后面全部节点
                current = current.getChildren().get("**");
                if (current.getNodeType().equals(NODE_TYPE_PATH_END)) {
                    if (current.getChildren().containsKey(method.toUpperCase())) {
                        current = current.getChildren().get(method.toUpperCase());
                        if (current.getNodeType().equals(NODE_TYPE_METHOD)) {
                            if (!current.getChildren().isEmpty()) {
                                Iterator<Map.Entry<String,Node>> iterator = current.getChildren().entrySet().iterator();
                                Map.Entry<String,Node> filterRole = iterator.next();
                                if (filterRole.getValue().getNodeType().equals(NODE_TYPE_FILTER_ROLES)) {
                                    return filterRole.getKey();
                                } else {
                                    return null;
                                }
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        //此时node应为pathEnd节点
        if (!current.getNodeType().equals(NODE_TYPE_PATH_END)) {
            return null;
        }
        // 匹配httpMethod
        if (!current.getChildren().containsKey(method.toUpperCase())) {
            return null;
        }
        current = current.getChildren().get(method.toUpperCase());
        if (!current.getNodeType().equals(NODE_TYPE_METHOD)) {
            return null;
        }
        // 取filterRoles
        if (!current.getChildren().isEmpty()) {
            Iterator<Map.Entry<String,Node>> iterator = current.getChildren().entrySet().iterator();
            Map.Entry<String,Node> filterRole = iterator.next();
            if (filterRole.getValue().getNodeType().equals(NODE_TYPE_FILTER_ROLES)) {
                return filterRole.getKey();
            } else {
                return null;
            }
        }
        return null;
    }


    private static class Node {

        public Node(String data, String nodeType) {
            this.data = data;
            this.nodeType = nodeType;
        }
        public Node(String data) {
            this.data = data;
            this.nodeType = NODE_TYPE_PATH_NODE;
        }
        /**
         *  当前节点的类型
         */
        private String nodeType;
        /**
         *  节点对应的数据
         */
        private String data;
        /**
         *  孩子节点
         */
        private Map<String, Node> children = new HashMap<String, Node>();

        public void insertChild(String data) {
            this.children.put(data,new Node(data));
        }
        public void insertChild(String data,String nodeType) {
            this.children.put(data,new Node(data,nodeType));
        }

        public String getNodeType() {
            return nodeType;
        }

        public void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Map<String, Node> getChildren() {
            return children;
        }

        public void setChildren(Map<String, Node> children) {
            this.children = children;
        }
    }

    private static class PathTireTreeHandler {
        private static TireTreePathMatcher instance = new TireTreePathMatcher();
    }

}
