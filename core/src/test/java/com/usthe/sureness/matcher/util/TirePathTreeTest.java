package com.usthe.sureness.matcher.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**  path tree util test
 * @author tomsun28
 * @date 12:22 2019-04-09
 */
public class TirePathTreeTest {

    private static TirePathTree root;

    @BeforeAll
    public static void setUp() {
        root = new TirePathTree();
    }

    @AfterAll
    public static void tearDown() {
        root.clearTree();
        root= null;
    }

    @Test
    public void buildTree() {
        Set<String> paths = new LinkedHashSet<>();
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
        // ignore resource
        paths.add("/**/*.html===post===[role8]");
        paths.add("/**/*.css===post===[role8]");
        paths.add("/**/*.js===post===[role8]");
        // lower upper roles
        paths.add("/api/role/book===get===[ROLE10]");
        // support ignore http method
        paths.add("/api/school/book===*===[role8]");
        paths.add("/api2/school/*===*===[role18]");
        // fix issue bug https://github.com/dromara/sureness/issues/132
        paths.add("/api/v6/book/*===get===[role1]");
        paths.add("/api/v6/book===post===[role2]");

        root.buildTree(paths);
        assertEquals(35, root.getResourceNum());
    }

    @Test
    public void searchPathFilterRoles() {
        buildTree();
        // multi path '/'
        assertEquals("[]", root.searchPathFilterRoles("/api/v2/book/node//===get"));
        // The configuration will not be overwritten or superimposed
        assertEquals("[role2]", root.searchPathFilterRoles("/api/v2/host===get"));
        // multi request method
        assertEquals("[role1]", root.searchPathFilterRoles("/api/v2/host===post"));
        assertEquals("[role2,role3]", root.searchPathFilterRoles("/api/v2/host===delete"));
        assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v2/host===put"));
        assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v1/host===put"));
        assertEquals("[role2,role3,role4]", root.searchPathFilterRoles("/api/v3/host===put"));
        // match *xxx eg: *.html
        assertEquals("[role1]", root.searchPathFilterRoles("/api/v6/host/content.html===get"));
        // priority: equals normal path > match *xxx
        assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v6/book/content.js===get"));
        assertEquals("[role2]", root.searchPathFilterRoles("/api/v6/book/other.js===get"));
        // match *
        assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v2/ha/host===put"));
        // priority: equals normal path > match *xxx > match *
        assertEquals("[role3,role4]", root.searchPathFilterRoles("/api/v4/mom/ha===put"));
        assertEquals("[role4]", root.searchPathFilterRoles("/api/v4html/mom/ha===put"));
        assertEquals("[role2,role4]", root.searchPathFilterRoles("/api/v6/mom/ha===put"));
        // match **
        assertEquals("[role5]", root.searchPathFilterRoles("/api/mi/tom/hello===put"));
        assertEquals("[role6]", root.searchPathFilterRoles("/api/mo/tom/hello/day/day===get"));
        assertEquals("[role7]", root.searchPathFilterRoles("/api/day/day/day/day/book/day/mo===put"));
        // priority: equals normal path > match *xxx > match * > match **
        assertEquals("[role5]", root.searchPathFilterRoles("/api/v5/day/book===put"));
        assertEquals("[role3]", root.searchPathFilterRoles("/api/demo/book/tom-app/egg===get"));
        assertEquals("[role1]", root.searchPathFilterRoles("/api/demo/book/tom/egg===get"));
        assertEquals("[role2]", root.searchPathFilterRoles("/api/demo/book/tom/good/egg===get"));
        assertEquals("[role6]", root.searchPathFilterRoles("/api/v5/mom/ha===put"));
        assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha/good===get"));
        assertEquals("[role9]", root.searchPathFilterRoles("/api/v5/mom/ha===get"));
        assertEquals("[role10]", root.searchPathFilterRoles("/er/swagger===get"));
        assertNull(root.searchPathFilterRoles("/api/v6/book/ha/good===put"));
        assertEquals("[role11]", root.searchPathFilterRoles("/swagger===get"));
        // ignore resource
        assertEquals("[role8]", root.searchPathFilterRoles("/content-ui.html===post"));
        assertEquals("[role8]", root.searchPathFilterRoles("/api/user/ui.js===post"));
        assertEquals("[role8]", root.searchPathFilterRoles("/node/v2/demo.css===post"));
        // lower upper roles
        assertEquals("[ROLE10]", root.searchPathFilterRoles("/api/role/book===get"));
        // support ignore http method
        assertEquals("[role8]", root.searchPathFilterRoles("/api/school/book===get"));
        assertEquals("[role8]", root.searchPathFilterRoles("/api/school/book===post"));
        assertEquals("[role8]", root.searchPathFilterRoles("/api/school/book===delete"));
        assertEquals("[role8]", root.searchPathFilterRoles("/api/school/book===put"));
        assertEquals("[role18]", root.searchPathFilterRoles("/api2/school/book===get"));
        assertEquals("[role18]", root.searchPathFilterRoles("/api2/school/book===post"));
        assertEquals("[role18]", root.searchPathFilterRoles("/api2/school/student===get"));
        assertEquals("[role18]", root.searchPathFilterRoles("/api2/school===delete"));
        // fix issue bug https://github.com/dromara/sureness/issues/132 
        assertEquals("[role1]", root.searchPathFilterRoles("/api/v6/book/3===get"));
        assertEquals("[role2]", root.searchPathFilterRoles("/api/v6/book===post"));

    }
}
