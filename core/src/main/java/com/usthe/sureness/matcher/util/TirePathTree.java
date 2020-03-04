package com.usthe.sureness.matcher.util;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 字典匹配树
 * @author tomsun28
 * @date 19:25 2019-01-18
 */
public class TirePathTree {

    private static final String NODE_TYPE_PATH_NODE = "pathNode";
//    /**
//     * path end also maybe path node
//     */
//    private static final String NODE_TYPE_PATH_END = "isPathEnd";
    private static final String NODE_TYPE_METHOD = "methodNode";
    private static final String NODE_TYPE_FILTER_ROLES = "filterRolesNode";
    private static final String URL_PATH_SPLIT = "/";
    private static final String MATCH_ONE = "*";
    private static final String MATCH_ALL = "**";
    private static final int PATH_NODE_NUM_3 = 3;
    private static final int PATH_NODE_NUM_2 = 2;
    private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("/+");

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
        path = PATH_SPLIT_PATTERN.matcher(path).replaceAll("/");
        path = path.substring(1);
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_2) {
            return null;
        }
        String[] urlPac = tmp[0].split("/");
        String method = tmp[1];

        // todo 基于ant的模式匹配   ? * **
        Node current = root;
        Stack<Node> stack = new Stack<>();
        stack.push(current);
        while (!stack.isEmpty()) {



        }


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


    private boolean search(int preAccessType, String[] urlPac, int pacIndex) {



        // todo 基于ant的模式匹配   ? * **
        Node current = root;
        Stack<Node> stack = new Stack<>();
        stack.push(current);
        while (!stack.isEmpty()) {


        }

        return true;
    }

    /**
     * description 插入节点
     * @param path path = /api/v1/host/detail===GET===[role2,role3,role4]
     */
    private void insertNode(String path) {
        if (path == null || "".equals(path) || !path.startsWith(URL_PATH_SPLIT)) {
            return;
        }
        path = PATH_SPLIT_PATTERN.matcher(path).replaceAll("/");
        // 去除第一个 /
        path = path.substring(1);
        String[] tmp = path.split("===");
        if (tmp.length != PATH_NODE_NUM_3) {
            return;
        }
        String[] urlPac = tmp[0].split(URL_PATH_SPLIT);
        String method = tmp[1];
        String supportRoles = tmp[2];
        Node current = root;
        // 开始插入URL节点
        for (String urlData : urlPac) {
            if (current.getChildren().containsKey(MATCH_ONE)) {
                current = current.getChildren().get(MATCH_ONE);
            } else if (MATCH_ONE.equals(urlData)) {
                Node matchOneNode = new Node(MATCH_ONE, NODE_TYPE_PATH_NODE);

                // 将其他非MATCH_ALL的node的孩子转移到MATCH_ONE
                Iterator<Map.Entry<String, Node>> iterator = current.getChildren().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Node> entry = iterator.next();
                    if (MATCH_ALL.equals(entry.getKey())
                            || NODE_TYPE_METHOD.equals(entry.getValue().getNodeType())
                            || NODE_TYPE_FILTER_ROLES.equals(entry.getValue().getNodeType())) {
                        continue;
                    }
                    if (entry.getValue().getChildren() != null) {
                        matchOneNode.insertChild(entry.getValue().getChildren().values());
                    }
                    iterator.remove();
                }
                current.insertChild(matchOneNode);
                current = matchOneNode;

            } else if (current.getChildren().containsKey(urlData.toLowerCase())) {
                current = current.getChildren().get(urlData.toLowerCase());
            } else {
                current.insertChild(urlData.toLowerCase());
                current = current.getChildren().get(urlData.toLowerCase());
            }
        }

        // 开始插入httpMethod节点
        if (!current.getChildren().containsKey(method.toLowerCase())) {
            current.insertChild(method.toLowerCase(), NODE_TYPE_METHOD);
        }
        current = current.getChildren().get(method.toLowerCase());
        // 开始插入 supportRoles 节点 supportRoles 保持其原始大小写
        // 每条资源只能对应一 supportRoles ,httpMethod下最多一个孩子节点
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

        private void insertChild(Node node) {
            this.children.put(node.data, node);
        }

        public void insertChild(Collection<Node> nodes) {
            if (nodes != null) {
                nodes.forEach(this::insertChild);
            }
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
