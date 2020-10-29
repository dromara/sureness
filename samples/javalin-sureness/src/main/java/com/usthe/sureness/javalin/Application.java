package com.usthe.sureness.javalin;

import com.usthe.sureness.DefaultSurenessConfig;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomsun28
 * @date 2020-09-15 23:59
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // init sureness default config
        new DefaultSurenessConfig();

        // init javalin
        Javalin app = Javalin.create().start(7000);

        // filter

        app.before(ctx -> {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(ctx.req);
            // when auth error , the exception throw, you should use app.exception() catch it and define return
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        });

        // when auth error , the exception throw, you should use app.exception() catch it and define return
        app.exception(ProcessorNotFoundException.class, (e, ctx) -> {
            log.debug("this request is illegal");
            ctx.status(401).result(e.getMessage());
        }).exception(UnknownAccountException.class, (e, ctx) -> {
            log.debug("this request is illegal");
            ctx.status(401).result(e.getMessage());
        }).exception(UnsupportedSubjectException.class, (e, ctx) -> {
            log.debug("this request is illegal");
            ctx.status(401).result(e.getMessage());
        }).exception(DisabledAccountException.class, (e, ctx) -> {
            log.debug("the account is disabled");
            ctx.status(401).result(e.getMessage());
        }).exception(ExcessiveAttemptsException.class, (e, ctx) -> {
            log.debug("the account is disabled");
            ctx.status(401).result(e.getMessage());
        }).exception(IncorrectCredentialsException.class, (e, ctx) -> {
            log.debug("this account credential is incorrect or expired");
            ctx.status(401).result(e.getMessage());
        }).exception(ExpiredCredentialsException.class, (e, ctx) -> {
            log.debug("this account credential is incorrect or expired");
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

        // routes
        app.get("/api/v3/host", ctx -> ctx.result("Hello World"));
        app.get("/api/v2/host", ctx -> ctx.result("get /api/v2/host"));
        app.post("/api/v2/host", ctx -> ctx.result("post /api/v2/host"));
        app.put("/api/v2/host", ctx -> ctx.result("put /api/v2/host"));
        app.delete("/api/v2/host", ctx -> ctx.result("delete /api/v2/host"));
        app.put("/api/mi/tom", ctx -> ctx.result("put /api/mi/tom"));
        app.get("/api/v1/getSource1", ctx -> ctx.result("get /api/v1/getSource1"));
        app.get("/api/v2/getSource2/book", ctx -> ctx.result("get /api/v2/getSource2/book"));
        app.get("/api/v1/source1", ctx -> ctx.result("get /api/v1/source1"));
        app.post("/api/v1/source1", ctx -> ctx.result("post /api/v1/source1"));
        app.put("/api/v1/source1", ctx -> ctx.result("put /api/v1/source1"));
        app.delete("/api/v1/source1", ctx -> ctx.result("delete /api/v1/source1"));
    }

}
