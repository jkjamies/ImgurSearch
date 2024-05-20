package com.jkjamies.imgur.core

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * Builder for configuring an [HttpClient]
 */
class HttpClientBuilder {
    private val plugins = mutableListOf<HttpClientPlugin<*, *>>()

    /**
     * Install a [plugin] into the [HttpClient]
     */
    fun install(plugin: HttpClientPlugin<*, *>) {
        plugins.add(plugin)
    }

    /**
     * Build the [HttpClient] with the configured plugins
     */
    @OptIn(ExperimentalSerializationApi::class)
    internal fun build(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        explicitNulls = false
                        ignoreUnknownKeys = true
                    },
                )
//                install(HttpTimeout) // Some endpoints may be heavy, for others - small or bust
//                install(LoggingPlugin)
                plugins.forEach { plugin -> install(plugin) }
            }
        }
    }
}

/**
 * Create a new [HttpClient] with the given [block] of configuration
 */
fun httpClient(block: HttpClientBuilder.() -> Unit): HttpClient = HttpClientBuilder().apply(block).build()
