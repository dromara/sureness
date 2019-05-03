package com.usthe.sureness.matcher.util;


import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;



/**
 * @author tomsun28
 * @date 12:22 2019-04-09
 */
public class TirePathTreeUtilTest {

    private static TirePathTreeUtil.Node root = null;
    @BeforeClass
    public static void setUp() throws Exception {
        root = new TirePathTreeUtil.Node("root");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        root = null;
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
    public void reBuildTree() {
    }

    @Test
    public void clearTree() {
    }

    @Test
    public void searchPathFilterRoles() {
    }
}