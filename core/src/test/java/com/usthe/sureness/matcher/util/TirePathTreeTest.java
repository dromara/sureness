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
        // '/' path
        paths.add("/api///v2////book///node//===get===[]");
        // The configuration will not be overwritten or superimposed
        paths.add("/api/v2/host===get===[role2]");
        paths.add("/api/v2/host===get===[role2,role3]");
        // multi request method
        paths.add("/api/v2/host===post===[role1]");
        paths.add("/api/v2/host===delete===[role2,role3]");
        paths.add("/api/v2/host===put===[role3,role4]");
        paths.add("/api/v1/host===put===[role2,role3,role4]");
        paths.add("/api/v3/host===put===[role2,role3,role4]");
        paths.add("/api/v2/detail===put===[role2,role3,role4]");
        paths.add("/api/v2/mom===put===[role2,role3,role4]");
        // match *xxx eg: *.html
        paths.add("/api/v6/host/*.html===get===[role1]");
        // priority: equals normal path > match *xxx
        paths.add("/api/v6/book/content.js===get===[role3,role4]");
        paths.add("/api/v6/book/*.js===get===[role2]");
        // match *
        paths.add("/api/*/ha/*===put===[role2,role4]");
        // priority: equals normal path > match *xxx > match *
        paths.add("/api/v4/mom/ha===put===[role3,role4]");
        paths.add("/api/*html/mom/ha===put===[role4]");
        paths.add("/api/*/mom/ha===put===[role2,role4]");
        //match **
        paths.add("/api/mi/**===put===[role5]");
        paths.add("/api/mo/**/day===get===[role6]");
        paths.add("/api/day/**/day/mo===put===[role7]");
        // priority: equals normal path > match *xxx > match * > match **
        paths.add("/api/v5/day/book===put===[role5]");
        paths.add("/api/v5/**===put===[role6]");
        paths.add("/api/demo/book/*app/egg===get===[role3]");
        paths.add("/api/demo/book/*/egg===get===[role1]");
        paths.add("/api/demo/book/**/egg===get===[role2]");
        paths.add("/**===get===[role9]");
        paths.add("/er/**/swagger===get===[role10]");
        paths.add("/swagger/**===get===[role11]");
        root.buildTree(paths);
        Assert.assertEquals(27, root.getResourceNum());
    }

    @Test
    public void searchPathFilterRoles() {
        buildTree();
        // multi path '/'
        Assert.assertEquals("[]", root.searchPathFilterRoles("/api/v2/book/node//===get"));
        // The configuration will not be overwritten or superimposed
        Assert.assertEquals("[role2]", root.searchPathFilterRoles("/api/v2/host===get"));
        // multi request method
        Assert.assertEquals("[role1]", root.searchPathFilterRoles("/api/v2/host===post"));
        Assert.assertEquals("[role2,role3]", root.searchPathFilterRoles("/api/v2/host===delete"));
        Assert.assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v2/host===put"));
        Assert.assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v1/host===put"));
        Assert.assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v3/host===put"));
        // match *xxx eg: *.html
        Assert.assertEquals("[role1]", root.searchPathFilterRoles("/api/v6/host/content.html===get"));
        // priority: equals normal path > match *xxx
        Assert.assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v6/book/content.js===get"));
        Assert.assertEquals("[role2]", root.searchPathFilterRoles("/api/v6/book/other.js===get"));
        // match *
        Assert.assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v2/ha/host===put"));
        // priority: equals normal path > match *xxx > match *
        Assert.assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v4/mom/ha===put"));
        Assert.assertEquals("[role4]", root.searchPathFilterRoles("/api/v4html/mom/ha===put"));
        Assert.assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v6/mom/ha===put"));
        // match **
        Assert.assertEquals("[role5]", root.searchPathFilterRoles("/api/mi/tom/hello===put"));
        Assert.assertEquals("[role6]", root.searchPathFilterRoles("/api/mo/tom/hello/day/day===get"));
        Assert.assertEquals("[role7]", root.searchPathFilterRoles("/api/day/day/day/day/book/day/mo===put"));
        // priority: equals normal path > match *xxx > match * > match **
        Assert.assertEquals("[role5]", root.searchPathFilterRoles("/api/v5/day/book===put"));
        Assert.assertEquals("[role3]", root.searchPathFilterRoles("/api/demo/book/tom-app/egg===get"));
        Assert.assertEquals("[role1]", root.searchPathFilterRoles("/api/demo/book/tom/egg===get"));
        Assert.assertEquals("[role2]", root.searchPathFilterRoles("/api/demo/book/tom/good/egg===get"));
        Assert.assertEquals("[role6]", root.searchPathFilterRoles("/api/v5/mom/ha===put"));
        Assert.assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha/good===get"));
        Assert.assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha===get"));
        Assert.assertEquals("[role10]", root.searchPathFilterRoles("/er/swagger===get"));
        Assert.assertNull(root.searchPathFilterRoles("/api/v6/book/ha/good===put"));
        Assert.assertEquals("[role11]", root.searchPathFilterRoles("/swagger===get"));

    }
}