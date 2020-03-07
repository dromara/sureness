package com.usthe.sureness.matcher.util;


import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**  path tree util test
 * @author tomsun28
 * @date 12:22 2019-04-09
 */
public class TirePathTreeTest {

    private static TirePathTree root;

    @BeforeClass
    public static void setUp() {
        root = new TirePathTree();
    }

    @AfterClass
    public static void tearDown() {
        root.clearTree();
        root= null;
    }

    @Test
    public void buildTree() {
        Set<String> paths = new HashSet<>();
        // 多'/'路径
        paths.add("/api///v2////book///node//===get===[]");
        // 配置不会覆盖,也不会叠加
        paths.add("/api/v2/host===get===[role2]");
        paths.add("/api/v2/host===get===[role2,role3]");
        // 多种请求方式
        paths.add("/api/v2/host===post===[role1]");
        paths.add("/api/v2/host===delete===[role2,role3]");
        paths.add("/api/v2/host===put===[role3,role4]");
        paths.add("/api/v1/host===put===[role2,role3,role4]");
        paths.add("/api/v3/host===put===[role2,role3,role4]");
        paths.add("/api/v2/detail===put===[role2,role3,role4]");
        paths.add("/api/v2/mom===put===[role2,role3,role4]");
        // *匹配
        paths.add("/api/*/ha/*===put===[role2,role4]");
        // 普通优先级>*
        paths.add("/api/v4/mom/ha===put===[role3,role4]");
        paths.add("/api/*/mom/ha===put===[role2,role4]");
        // **匹配
        paths.add("/api/mi/**===put===[role5]");
        paths.add("/api/mo/**/day===get===[role6]");
        paths.add("/api/day/**/day/mo===put===[role7]");
        // 普通优先级>*>**
        paths.add("/api/v5/day/book===put===[role5]");
        paths.add("/api/v5/**===put===[role6]");
        paths.add("/api/demo/book/*/egg===get===[role1]");
        paths.add("/api/demo/book/**/egg===get===[role2]");
        paths.add("/**===get===[role9]");
        root.buildTree(paths);
        Assert.assertEquals(20, root.getResourceNum());
    }

    @Test
    public void searchPathFilterRoles() {
        buildTree();
        // 多'/'路径
        Assert.assertEquals("[]", root.searchPathFilterRoles("/api/v2/book/node//===get"));
        // 配置不会覆盖,也不会叠加
        Assert.assertEquals("[role2]", root.searchPathFilterRoles("/api/v2/host===get"));
        // 多种请求方式
        Assert.assertEquals("[role1]", root.searchPathFilterRoles("/api/v2/host===post"));
        Assert.assertEquals("[role2,role3]", root.searchPathFilterRoles("/api/v2/host===delete"));
        Assert.assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v2/host===put"));
        Assert.assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v1/host===put"));
        Assert.assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v3/host===put"));
        // *匹配
        Assert.assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v2/ha/host===put"));
        // 普通优先级>*
        Assert.assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v4/mom/ha===put"));
        Assert.assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v6/mom/ha===put"));
        // **匹配
        Assert.assertEquals("[role5]", root.searchPathFilterRoles("/api/mi/tom/hello===put"));
        Assert.assertEquals("[role6]", root.searchPathFilterRoles("/api/mo/tom/hello/day/day===get"));
        Assert.assertEquals("[role7]", root.searchPathFilterRoles("/api/day/day/day/day/book/day/mo===put"));
        // 普通优先级>*>**
        Assert.assertEquals("[role5]", root.searchPathFilterRoles("/api/v5/day/book===put"));
        Assert.assertEquals("[role1]", root.searchPathFilterRoles("/api/demo/book/tom/egg===get"));
        Assert.assertEquals("[role2]", root.searchPathFilterRoles("/api/demo/book/tom/good/egg===get"));
        Assert.assertEquals("[role6]", root.searchPathFilterRoles("/api/v5/mom/ha===put"));
        Assert.assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha/good===get"));
        Assert.assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha===get"));
        Assert.assertNull(root.searchPathFilterRoles("/api/v6/book/ha/good===put"));

    }
}