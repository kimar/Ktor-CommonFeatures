package io.kida.ktor.commonfeatures

import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.header
import io.ktor.response.respond
import io.ktor.util.AttributeKey

final class BearerAuthentication(private val token: String): ApplicationFeature<ApplicationCallPipeline, Unit, Unit> {
    override val key = AttributeKey<Unit>("Bearer Authentication")
    override fun install(pipeline: ApplicationCallPipeline, configure: Unit.() -> Unit) {
        pipeline.intercept(ApplicationCallPipeline.Infrastructure) {
            when(call.request.header("Authorization")) {
                "Bearer $token" -> proceed()
                else -> {
                    call.respond(HttpStatusCode.Unauthorized, HttpStatusCode.Unauthorized.description)
                    finish()
                }
            }
        }
    }
}