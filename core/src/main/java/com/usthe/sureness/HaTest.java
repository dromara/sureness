package com.usthe.sureness;

import com.usthe.sureness.matcher.util.TirePathTreeUtil;


import java.util.HashSet;
import java.util.Set;

/**
 * @author tomsun28
 * @date 22:49 2019-01-09
 */
public class HaTest {


    public static void main(String[] args) {
        TirePathTreeUtil pathTireTree = TirePathTreeUtil.getInstance();
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
        pathTireTree.reBuildTree(paths);
        String filterRole = pathTireTree.searchPathFilterRoles("/api/v2/host===get");
        String var1 = pathTireTree.searchPathFilterRoles("/api/v1/mom===put");
        String var2 = pathTireTree.searchPathFilterRoles("/api/v2/host===put");
        String var3 = pathTireTree.searchPathFilterRoles("/api/v2/details===put");
        String var4 = pathTireTree.searchPathFilterRoles("/api/v2/detail===put");
        String var5 = pathTireTree.searchPathFilterRoles("/api/dd/mom/ha===put");
        String var6 = pathTireTree.searchPathFilterRoles("/api/mi/mom/ha===put");

    }
}
