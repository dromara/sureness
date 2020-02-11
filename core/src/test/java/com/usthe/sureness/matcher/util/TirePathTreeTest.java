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
        paths.add("/api/v2/host===post===[role2,role3,role4]");
        paths.add("/api/v2/host===get===[role2,role3,role4]");
        paths.add("/api/v2/host===delete===[role2,role3,role4]");
        paths.add("/api/v2/host===put===[role2,role3,role4]");
        paths.add("/api/v1/host===put===[role2,role3,role4]");
        paths.add("/api/v3/host===put===[role2,role3,role4]");
        paths.add("/api/v2/detail===put===[role2,role3,role4]");
        paths.add("/api/v2/mom===put===[role2,role3,role4]");
        paths.add("/api/*/mom/ha===put===[role2,role3,role4]");
        paths.add("/api/mi/**===put===[role2,role3,role4]");
        root.buildTree(paths);
        Assert.assertEquals(root.getResourceNum(), 1);
    }

    @Test
    public void searchPathFilterRoles() {
        buildTree();
        String filterRole = root.searchPathFilterRoles("/api/v2/host===get");
        Assert.assertEquals(filterRole, "[role2,role3,role4]");
        String var1 = root.searchPathFilterRoles("/api/v1/mom===put");
        Assert.assertNull(var1);
        String var2 = root.searchPathFilterRoles("/api/v2/host===put");
        Assert.assertEquals(var2, "[role2,role3,role4]");
        String var3 = root.searchPathFilterRoles("/api/v2/detail===put");
        Assert.assertEquals(var3, "[role2,role3,role4]");
        String var4 = root.searchPathFilterRoles("/api/v2/details===put");
        Assert.assertNull(var4);
        String var5 = root.searchPathFilterRoles("/api/dd/mom/ha===put");
        Assert.assertEquals(var5, "[role2,role3,role4]");
        String var6 = root.searchPathFilterRoles("/api/mi/mom/ha===put");
        Assert.assertEquals(var6, "[role2,role3,role4]");

    }
}