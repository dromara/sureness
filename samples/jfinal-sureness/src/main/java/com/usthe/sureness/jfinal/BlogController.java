package com.usthe.sureness.jfinal;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;

/**
 * @author tomsun28
 * @date 2021/4/30 18:52
 */
@Before(SurenessInterceptor.class)
@Path("/blog")
public class BlogController extends Controller {

    public void index() {
        renderText("get bolg api success");
    }

    public void add() {
        renderText("add bolg api success");
    }


    public void update() {
        renderText("update bolg api success");
    }

    public void delete() {
        renderText("delete bolg api success");
    }
}
