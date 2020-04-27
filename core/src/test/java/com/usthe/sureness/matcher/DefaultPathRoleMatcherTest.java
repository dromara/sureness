package com.usthe.sureness.matcher;

import com.usthe.sureness.matcher.util.TirePathTree;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.PasswordSubject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;


/**
 * @author tomsun28
 * @date 18:07 2020-03-08
 */
public class DefaultPathRoleMatcherTest {

    private static DefaultPathRoleMatcher pathRoleMatcher;
    private static Set<String> paths;
    private static Set<String> excludePaths;

    @BeforeClass
    public static void setUp() {
        pathRoleMatcher = new DefaultPathRoleMatcher();
        paths = new HashSet<>();
        paths.add("/api///v2////book///node//===get===[]");
        paths.add("/api/v2/host===get===[role2]");
        paths.add("/api/v2/host===get===[role2,role3]");
        paths.add("/api/v2/host===post===[role1]");
        paths.add("/api/v2/host===delete===[role2,role3]");
        paths.add("/api/v2/host===put===[role3,role4]");
        paths.add("/api/v1/host===put===[role2,role3,role4]");
        paths.add("/api/v3/host===put===[role2,role3,role4]");
        paths.add("/api/v2/detail===put===[role2,role3,role4]");
        paths.add("/api/v2/mom===put===[role2,role3,role4]");
        paths.add("/api/*/ha/*===put===[role2,role4]");

        excludePaths = new HashSet<>();
        excludePaths.add("/api/v2/detail===put");
        excludePaths.add("/api/v2/mom===put");
        excludePaths.add("/api/*/ha/*===put");
        excludePaths.add("/api/v4/mom/ha===put");
        excludePaths.add("/api/*/mom/ha===put");
        excludePaths.add("/api/mi/**===put");
        excludePaths.add("/api/mo/**/day===get");
        excludePaths.add("/api/day/**/day/mo===put");
    }

    @AfterClass
    public static void tearDown() {
        pathRoleMatcher = null;
    }

    @Test
    public void matchRole() {
        buildTree();
        Subject subject = PasswordSubject.builder("tom", "123")
                .setTargetResource("/api/v2/host===get").build();
        pathRoleMatcher.matchRole(subject);
        assertNotNull(subject.getSupportRoles());
        assertTrue((subject.getSupportRoles() instanceof List));
    }

    @Test
    public void buildTree() {
        PathTreeProvider provider = createNiceMock(PathTreeProvider.class);
        expect(provider.providePathData()).andReturn(paths);
        replay(provider);
        pathRoleMatcher.setPathTreeProvider(provider);
        pathRoleMatcher.buildTree();
        verify(provider);
    }

    @Test
    public void isExcludedResource() {
        loadExcludedResource();
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getRequestURI()).andReturn("/api/v2/detail");
        expect(request.getMethod()).andReturn("put");
        replay(request);
        assertTrue(pathRoleMatcher.isExcludedResource(request));
        verify(request);

        request = createNiceMock(HttpServletRequest.class);
        expect(request.getRequestURI()).andReturn("/book/v2/detail");
        expect(request.getMethod()).andReturn("put");
        replay(request);
        assertFalse(pathRoleMatcher.isExcludedResource(request));
        verify(request);
    }

    @Test
    public void loadExcludedResource() {
        PathTreeProvider provider = createNiceMock(PathTreeProvider.class);
        expect(provider.provideExcludedResource()).andReturn(excludePaths);
        replay(provider);
        pathRoleMatcher.setPathTreeProvider(provider);
        pathRoleMatcher.buildTree();
        verify(provider);
    }
}