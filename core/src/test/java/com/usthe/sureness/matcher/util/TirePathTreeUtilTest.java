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
public class TirePathTreeUtilTest {

    private static TirePathTreeUtil.Node root = null;

    @BeforeClass
    public static void setUp() {
        root = new TirePathTreeUtil.Node("root");
    }

    @AfterClass
    public static void tearDown() {
        root.getChildren().clear();
        root= null;
    }

    @Test
    public void buildTree() {
        Set<String> paths = new HashSet<>();
        paths.add("/api/v2/host===post===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===get===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===delete===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v1/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v3/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v2/detail===put===jwt[role2,role3,role4]");
        paths.add("/api/v2/mom===put===jwt[role2,role3,role4]");
        paths.add("/api/*/mom/ha===put===jwt[role2,role3,role4]");
        paths.add("/api/mi/**===put===jwt[role2,role3,role4]");
        TirePathTreeUtil.buildTree(paths, root);
        Assert.assertEquals(root.getChildren().size(), 1);
    }

    @Test
    public void searchPathFilterRoles() {
        buildTree();
        String filterRole = TirePathTreeUtil.searchPathFilterRoles("/api/v2/host===get", root);
        Assert.assertEquals(filterRole, "jwt[role2,role3,role4]".toUpperCase());
        String var1 = TirePathTreeUtil.searchPathFilterRoles("/api/v1/mom===put", root);
        Assert.assertNull(var1);
        String var2 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/host===put", root);
        Assert.assertEquals(var2, "jwt[role2,role3,role4]".toUpperCase());
        String var3 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/detail===put", root);
        Assert.assertEquals(var3, "jwt[role2,role3,role4]".toUpperCase());
        String var4 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/details===put", root);
        Assert.assertNull(var4);
        String var5 = TirePathTreeUtil.searchPathFilterRoles("/api/dd/mom/ha===put", root);
        Assert.assertEquals(var5, "jwt[role2,role3,role4]".toUpperCase());
        String var6 = TirePathTreeUtil.searchPathFilterRoles("/api/mi/mom/ha===put", root);
        Assert.assertEquals(var6, "jwt[role2,role3,role4]".toUpperCase());

    }
}