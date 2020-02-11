package com.usthe.sureness.matcher.util;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 字典匹配树
 * @author tomsun28
 * @date 19:25 2019-01-18
 */
public class TirePathTree {

    private static final String NODE_TYPE_PATH_NODE = "pathNode";
    private static final String NODE_TYPE_PATH_END = "isPathEnd";
    private static final String NODE_TYPE_METHOD = "methodNode";
    private static final String NODE_TYPE_FILTER_ROLES = "filterRolesNode";
    private static final String URL_PATH_SPLIT = "/";
    private static final int PATH_NODE_NUM_3 = 3;
    private static final int PATH_NODE_NUM_2 = 2;

    /**
     * 根节点
     */
    private Node root;

    public TirePathTree() {
        this.root = new Node("root");
    }

    /**
     * 新建字典匹配树
     * @param paths 资路径
     */
    public void buildTree(Set<String> paths) {
        clearTree();
        for (String path : paths) {
            insertNode(path);
        }
    }

    /**
     * 清空字典树
     */
    public void clearTree() {
        root.getChildren().clear();
    }

    /**
     * 获取当前匹配树存在的匹配资源(URL+METHOD)数量
     * @return int 资源数量
     */
    public int getResourceNum() {
        return 0;
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
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_2) {
            return null;
        }
        String[] urlPac = tmp[0].split("/");
        if (urlPac.length > 1) {
            urlPac = Arrays.copyOfRange(urlPac, 1, urlPac.length);
        }
        String method = tmp[1];
        Node current = root;
        //支持基于ant的模式匹配
        for (String data : urlPac) {
            if (current.getChildren().containsKey(data.toLowerCase())) {
                current = current.getChildren().get(data.toLowerCase());
            } else if (current.getChildren().containsKey("*")) {
                // * 代表匹配一个data节点
                current = current.getChildren().get("*");
            } else if (current.getChildren().containsKey("**")) {
                // ** 代表匹配后面全部节点
                current = current.getChildren().get("**");
                if (NODE_TYPE_PATH_END.equals(current.getNodeType())) {
                    if (current.getChildren().containsKey(method.toLowerCase())) {
                        current = current.getChildren().get(method.toLowerCase());
                        if (NODE_TYPE_METHOD.equals(current.getNodeType())) {
                            if (!current.getChildren().isEmpty()) {
                                Iterator<Map.Entry<String,Node>> iterator = current.getChildren().entrySet().iterator();
                                Map.Entry<String,Node> filterRole = iterator.next();
                                if (NODE_TYPE_FILTER_ROLES.equals(filterRole.getValue().getNodeType())) {
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
        if (!NODE_TYPE_PATH_END.equals(current.getNodeType())) {
            return null;
        }
        // 匹配httpMethod
        if (!current.getChildren().containsKey(method.toLowerCase())) {
            return null;
        }
        current = current.getChildren().get(method.toLowerCase());
        if (!NODE_TYPE_METHOD.equals(current.getNodeType())) {
            return null;
        }
        // 取filterRoles
        if (!current.getChildren().isEmpty()) {
            Iterator<Map.Entry<String,Node>> iterator = current.getChildren().entrySet().iterator();
            Map.Entry<String,Node> filterRole = iterator.next();
            if (NODE_TYPE_FILTER_ROLES.equals(filterRole.getValue().getNodeType())) {
                return filterRole.getKey();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * description 插入节点
     * @param path path = /api/v1/host/detail===GET===jwt[role2,role3,role4]
     */
    private void insertNode(String path) {
        if (path == null || "".equals(path) || !path.startsWith(URL_PATH_SPLIT)) {
            return;
        }
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_3) {
            return;
        }
        String[] urlPac = tmp[0].split(URL_PATH_SPLIT);
        if (urlPac.length > 1) {
            // 去除第一位的空 ""
            urlPac = Arrays.copyOfRange(urlPac, 1, urlPac.length);
        }
        String method = tmp[1];
        String supportRoles = tmp[2];
        Node current = root;
        //开始插入URL节点
        for (String urlData : urlPac) {
            if (!current.getChildren().containsKey(urlData.toLowerCase())) {
                current.insertChild(urlData.toLowerCase());
            }
            current = current.getChildren().get(urlData.toLowerCase());
        }
        current.setNodeType(NODE_TYPE_PATH_END);
        //开始插入httpMethod节点
        if (!current.getChildren().containsKey(method.toLowerCase())) {
            current.insertChild(method.toLowerCase(), NODE_TYPE_METHOD);
        }
        current = current.getChildren().get(method.toLowerCase());
        //开始插入 supportRoles 节点 supportRoles 保持其原始大小写
        //每条资源只能对应一 supportRoles ,httpMethod下最多一个孩子节点
        if (current.getChildren().isEmpty()) {
            current.insertChild(supportRoles, NODE_TYPE_FILTER_ROLES);
        }

    }

    /**
     * 树节点类
     */
    private class Node {

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
            this.children.put(data,new Node(data));
        }

        private void insertChild(String data,String nodeType) {
            this.children.put(data,new Node(data,nodeType));
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
