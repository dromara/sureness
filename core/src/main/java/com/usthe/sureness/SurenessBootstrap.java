package com.usthe.sureness;

import com.usthe.sureness.provider.PathTreeProvider;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.util.TireTreePathMatcher;

import java.util.HashSet;
import java.util.Set;


/* *
 * @Author tomsun28
 * @Description
 * @Date 20:43 2019-02-25
 */
public class SurenessBootstrap {

    private static PathTreeProvider pathTreeProvider;

    public static void initBootstrap() {
        preInit();
        initPathTree();
    }

    public static void checkedIn(SubjectAuToken auToken) {
        String targetUri = (String)auToken.getTargetResource();


    }

    private static void preInit() {

    }


    private static void initPathTree() {
        Set<String> pathData = getPathData();
        TireTreePathMatcher.getInstance().buildTree(pathData);
    }

    private static Set<String> getPathData() {
        return pathTreeProvider.providPathData();
    }

}
