package com.usthe.sureness;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.matcher.util.TirePathTreeUtil;

import java.util.Set;


/**
 * @author tomsun28
 * @date 20:43 2019-02-25
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

    }

    private static Set<String> getPathData() {
        return pathTreeProvider.providePathData();
    }

}
