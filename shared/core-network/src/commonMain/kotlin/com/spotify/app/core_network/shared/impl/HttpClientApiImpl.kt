package com.spotify.app.core_network.shared.impl

import com.spotify.app.core_logger.shared.api.LoggerApi
import com.spotify.app.core_network.shared.CoreNetworkBuildKonfig
import com.spotify.app.core_network.shared.api.HttpClientApi
import com.spotify.app.core_preferences.shared.impl.util.PreferenceUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.Endpoints
import com.spotify.app.core_network.shared.impl.model.RefreshTokenRequest
import com.spotify.app.core_network.shared.impl.model.RefreshTokenResponse
import io.ktor.client.request.host
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class HttpClientApiImpl(
    private val shouldEnableLogging: Boolean,
    private val loggerApi: LoggerApi,
    private val preferenceUtil: PreferenceUtil
) : HttpClientApi {

    companion object {
        private const val SOCKET_TIMEOUT_MILLIS = 60_000L
        private const val CONNECT_TIMEOUT_MILLIS = 60_000L
        private const val REQUEST_TIMEOUT_MILLIS = 60_000L
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
            useAlternativeNames = true
            encodeDefaults = true
            explicitNulls = false
        }
    }

    override fun getHttpClient(httpEngineProvider: HttpEngineProvider): HttpClient {
        return HttpClient(httpEngineProvider.clientEngine(shouldEnableLogging)) {
            expectSuccess = true

            //Default Request
            defaultRequest {
                host = CoreNetworkBuildKonfig.BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                }
                contentType(ContentType.Application.Json)
            }

            //Authenticator
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = preferenceUtil.getAccessToken()!!,
                            refreshToken = preferenceUtil.getRefreshToken()!!
                        )
                    }
                    refreshTokens {
                        val refreshTokenResponse = client.post {
                            host = CoreNetworkBuildKonfig.BASE_URL_AUTH
                            url(Endpoints.REFRESH_TOKEN)
                            contentType(ContentType.parse("application/x-www-form-urlencoded"))
                            setBody(
                                RefreshTokenRequest(
                                    grantType = "",
                                    refreshToken = preferenceUtil.getRefreshToken()!!,
                                    clientId = ""
                                )
                            )
                            markAsRefreshTokenRequest()
                        }.body<RefreshTokenResponse>()

                        val accessToken = refreshTokenResponse.accessToken
                        val refreshToken = refreshTokenResponse.refreshToken

                        runBlocking {
                            preferenceUtil.setAccessToken(accessToken)
                            preferenceUtil.setRefreshToken(refreshToken)
                        }

                        BearerTokens(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    }
                }
            }

            //Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
            }

            //Logging
            if (shouldEnableLogging) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            loggerApi.logD(message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }

            //Response Observer
            if (shouldEnableLogging) {
                install(ResponseObserver) {
                    onResponse {
                        loggerApi.logD(it.bodyAsText())
                    }
                }
            }

            //Serialization
            install(ContentNegotiation) {
                json(json)
            }
        }
    }
}