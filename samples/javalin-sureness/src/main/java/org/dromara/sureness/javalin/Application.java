package org.dromara.sureness.javalin;

import io.javalin.Javalin;

import org.dromara.sureness.DefaultSurenessConfig;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.dromara.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * @author tomsun28
 * @date 2020-09-15 23:59
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // init sureness default config
        new DefaultSurenessConfig();

        // init javalin
        Javalin app = Javalin.create().start(8088);

        // intercept all rest requests for authenticating and authorizing
        app.before(ctx -> {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(ctx.req);
            // when auth error , the exception throw, you should use app.exception() catch it and define return
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        });

        app.after(ctx ->  SurenessContextHolder.unbindSubject());

        // when auth error , the exception throw, you should use app.exception() catch it and define return
        app.exception(UnknownAccountException.class, (e, ctx) -> {
            log.debug("this request user account not exist");
            ctx.status(401).result(e.getMessage());
        }).exception(IncorrectCredentialsException.class, (e, ctx) -> {
            log.debug("this account credential is incorrect");
            ctx.status(401).result(e.getMessage());
        }).exception(ExpiredCredentialsException.class, (e, ctx) -> {
            log.debug("this account credential expired");
            ctx.status(401).result(e.getMessage());
        }).exception(NeedDigestInfoException.class, (e, ctx) -> {
            log.debug("you should try once again with digest auth information");
            ctx.status(401).header("WWW-Authenticate", e.getAuthenticate());
        }).exception(UnauthorizedException.class, (e, ctx) -> {
            log.debug("this account can not access this resource");
            ctx.status(403).result(e.getMessage());
        }).exception(Exception.class, (e, ctx) -> {
            log.error("other exception happen: ", e);
            ctx.status(500).result(e.getMessage());
        });

        // issue jwt rest api
        app.get("/auth/token", ctx -> {
            SubjectSum subjectSum = SurenessContextHolder.getBindSubject();
            if (subjectSum == null) {
                ctx.result("Please auth!");
            } else {
                String principal = (String) subjectSum.getPrincipal();
                List<String> roles = (List<String>) subjectSum.getRoles();
                // issue jwt
                String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), principal,
                        "token-server", 3600L, roles);
                ctx.result(jwt);
            }
        });

        // simple rest api
        app.routes(() ->
            path("api", () -> {
                path("v3", () -> {
                    get("host", ctx -> ctx.result("get /api/v3/host success"));
                    put("book", ctx -> ctx.result("put /api/v3/book success"));
                });
                path("v2", () -> {
                    path("host", () -> {
                        get(ctx -> ctx.result("get /api/v2/host success"));
                        post(ctx -> ctx.result("post /api/v2/host success"));
                        put(ctx -> ctx.result("put /api/v2/host success"));
                        delete(ctx -> ctx.result("delete /api/v2/host success"));
                    });
                });
                path("v1", () -> {
                    path("source1", () -> {
                        get(ctx -> ctx.result("get /api/v1/source1 success"));
                        post(ctx -> ctx.result("post /api/v1/source1 success"));
                        put(ctx -> ctx.result("put /api/v1/source1 success"));
                        delete(ctx -> ctx.result("delete /api/v1/source1 success"));
                    });
                });
            }));
    }
}
