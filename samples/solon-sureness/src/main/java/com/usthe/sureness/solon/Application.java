package com.usthe.sureness.solon;

import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessContextHolder;
import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.core.handle.RenderManager;

import java.util.List;
import java.util.UUID;

/**
 * start up
 * @author tomsun28
 * @date 2021/5/7 19:53
 */
public class Application {

    public static void main(String[] args){
        SolonApp app = Solon.start(Application.class, args);

        // issue jwt rest api
        app.get("/auth/token", ctx -> {
            SubjectSum subjectSum = SurenessContextHolder.getBindSubject();
            if (subjectSum == null) {
                RenderManager.global.render("Please auth!", ctx);
            } else {
                String principal = (String) subjectSum.getPrincipal();
                List<String> roles = (List<String>) subjectSum.getRoles();
                // issue jwt
                String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), principal,
                        "token-server", 3600L, roles);
                RenderManager.global.render(jwt, ctx);
            }
        });

        app.after("/**", context -> SurenessContextHolder.unbindSubject());
    }
}
