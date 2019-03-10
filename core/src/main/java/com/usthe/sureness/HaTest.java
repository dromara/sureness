package com.usthe.sureness;

import com.sun.tools.javac.util.StringUtils;
import com.usthe.sureness.matcher.util.TirePathTreeUtil;


import java.util.HashSet;
import java.util.Set;

/**
 * @author tomsun28
 * @date 22:49 2019-01-09
 */
public class HaTest {


    public static void main(String[] args) {
        TirePathTreeUtil.Node root = new TirePathTreeUtil.Node("root");


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

        String filterRole = TirePathTreeUtil.searchPathFilterRoles("/api/v2/host===get", root);
        String var1 = TirePathTreeUtil.searchPathFilterRoles("/api/v1/mom===put", root);
        String var2 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/host===put", root);
        String var3 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/details===put", root);
        String var4 = TirePathTreeUtil.searchPathFilterRoles("/api/v2/detail===put", root);
        String var5 = TirePathTreeUtil.searchPathFilterRoles("/api/dd/mom/ha===put", root);
        String var6 = TirePathTreeUtil.searchPathFilterRoles("/api/mi/mom/ha===put", root);

        String vat = "[]";
        System.out.println(vat.substring(1, vat.length()-1));
//        String[] ss = vat.substring(1, vat.length()-2).split(",");
//        System.out.println(ss[0] + ss[1] + ss[2]);

    }
}
