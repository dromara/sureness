package com.usthe.sureness.demo

import org.dromara.sureness.DefaultSurenessConfig
import org.dromara.sureness.mgt.SurenessSecurityManager
import org.dromara.sureness.processor.exception.*
import org.dromara.sureness.util.SurenessContextHolder
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.EngineAPI
import io.ktor.server.engine.embeddedServer
import io.ktor.server.servlet.AsyncServletApplicationRequest
import io.ktor.server.tomcat.Tomcat
import java.util.*


@EngineAPI
fun main(args: Array<String>) {
    embeddedServer(Tomcat, port = 8080){main()}.start(wait = true)
}


@EngineAPI
fun Application.main() {
    // init sureness default config
    DefaultSurenessConfig()

    intercept(ApplicationCallPipeline.Call) {
        try {
            val request = (call.request as AsyncServletApplicationRequest).servletRequest
            val subject = SurenessSecurityManager.getInstance().checkIn(request)
            // if auth success contextHolder bind the subject
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject)
                log.debug("auth success!")
            }
        } catch (e4: UnknownAccountException) {
            log.debug("this request account info is illegal")
            call.respond(HttpStatusCode.Unauthorized, e4.localizedMessage)
            return@intercept finish()

        } catch (e3: IncorrectCredentialsException) {
            log.debug("this account credential is incorrect")
            call.respond(HttpStatusCode.Unauthorized, e3.localizedMessage)
            return@intercept finish()

        } catch (e3: ExpiredCredentialsException) {
            log.debug("this account credential is expired")
            call.respond(HttpStatusCode.Unauthorized, e3.localizedMessage)
            return@intercept finish()

        } catch (e4: NeedDigestInfoException) {
            log.debug("you should try once again with digest auth information")
            call.response.header("WWW-Authenticate", e4.authenticate)
            call.response.status(HttpStatusCode.Unauthorized)
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e5: UnauthorizedException) {
            log.debug("this account can not access this resource")
            call.respond(HttpStatusCode.Forbidden, e5.localizedMessage)
            return@intercept finish()

        } catch (e: RuntimeException) {
            log.error("other exception happen: ", e)
            call.respond(HttpStatusCode.Conflict, e.localizedMessage)
            return@intercept finish()
        }
    }

    routing {
        get("/api/v1/bar/{id}") {
            call.respondText("access " + call.request.uri + " success")
        }
        post("/api/v1/bar") {
            call.respondText("access " + call.request.uri + " success")
        }
        put("/api/v2/bar") {
            call.respondText("access " + call.request.uri + " success")
        }
        get("/api/v2/foo") {
            call.respondText("access " + call.request.uri + " success")
        }
        delete("/api/v2/foo") {
            call.respondText("access " + call.request.uri + " success")
        }
        get("/api/v3/foo") {
            call.respondText("access " + call.request.uri + " success")
        }
    }
}