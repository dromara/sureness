package com.usthe.sureness.demo

import com.usthe.sureness.DefaultSurenessConfig
import com.usthe.sureness.mgt.SurenessSecurityManager
import com.usthe.sureness.processor.exception.*
import com.usthe.sureness.subject.support.PasswordSubject
import com.usthe.sureness.util.SurenessContextHolder
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.EngineAPI
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty



@EngineAPI
fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8081){main()}.start(wait = true)
}


@EngineAPI
fun Application.main() {
    // init sureness default config
    DefaultSurenessConfig()

    intercept(ApplicationCallPipeline.Call) {
        try {
            val request = call.request
            val subject = SurenessSecurityManager.getInstance().checkIn(request)
            // if auth success contextHolder bind the subject
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject)
                log.debug("auth success!")
            }
        } catch (e4: ProcessorNotFoundException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e4: UnknownAccountException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e4: UnsupportedSubjectException) {
            log.debug("this request is illegal")
            call.respondText(e4.localizedMessage)
            return@intercept finish()

        } catch (e2: DisabledAccountException) {
            log.debug("the account is disabled")
            call.respondText(e2.localizedMessage)
            return@intercept finish()

        } catch (e2: ExcessiveAttemptsException) {
            log.debug("the account is disabled")
            call.respondText(e2.localizedMessage)
            return@intercept finish()

        } catch (e3: IncorrectCredentialsException) {
            log.debug("this account credential is incorrect or expired")
            call.respondText(e3.localizedMessage)
            return@intercept finish()

        } catch (e3: ExpiredCredentialsException) {
            log.debug("this account credential is incorrect or expired")
            call.respondText(e3.localizedMessage)
            return@intercept finish()

        } catch (e5: UnauthorizedException) {
            log.debug("this account can not access this resource")
            call.respondText(e5.localizedMessage)
            return@intercept finish()

        } catch (e: RuntimeException) {
            log.error("other exception happen: ", e)
            call.respondText(e.localizedMessage)
            return@intercept finish()
        }
    }

    routing {
        get("/api/v3/host") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
        get("/api/v2/host") {
            call.respondText("get /api/v2/host")
        }
        post("/api/v2/host") {
            call.respondText("post /api/v2/host")
        }
        put("/api/v2/host") {
            call.respondText("put /api/v2/host")
        }
        delete("/api/v2/host") {
            call.respondText("delete /api/v2/host")
        }
        put("/api/mi/tom") {
            call.respondText("put /api/mi/tom")
        }
        get("/api/v1/getSource1") {
            call.respondText("get /api/v1/getSource1")
        }
        get("/api/v2/getSource2/book") {
            call.respondText("get /api/v2/getSource2/book")
        }
        get("/api/v1/source1") {
            call.respondText("get /api/v1/source1")
        }
        post("/api/v1/source1") {
            call.respondText("post /api/v1/source1")
        }
        put("/api/v1/source1") {
            call.respondText("put /api/v1/source1")
        }
        delete("/api/v1/source1") {
            call.respondText("delete /api/v1/source1")
        }
    }
}