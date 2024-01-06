package com.spotify.app.core_network.shared.impl

import com.spotify.app.core_logger.shared.api.LoggerApi
import com.spotify.app.core_network.shared.CoreNetworkBuildKonfig
import com.spotify.app.core_network.shared.api.HttpClientApi
import com.spotify.app.core_network.shared.impl.model.RefreshTokenRequest
import com.spotify.app.core_network.shared.impl.model.RefreshTokenResponse
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.Endpoints
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.NetworkApiConfig
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.AuthHeader
import com.spotify.app.core_preferences.shared.api.PreferenceUtilApi
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
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class HttpClientApiImpl(
    private val shouldEnableLogging: Boolean,
    private val json: Json,
    private val loggerApi: LoggerApi,
    private val preferenceUtilApi: PreferenceUtilApi,
) : HttpClientApi {

    private val httpEngineProvider by lazy {
        HttpEngineProvider()
    }

    override fun getHttpClient(): HttpClient {
        return HttpClient(httpEngineProvider.clientEngine(shouldEnableLogging)) {
            expectSuccess = true

            //Default Request
            defaultRequest {
                host = CoreNetworkBuildKonfig.BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
                }
                contentType(ContentType.Application.Json)

                //Access Token
                val accessToken = runBlocking { preferenceUtilApi.getAccessToken() }
                if (accessToken.isNullOrBlank().not()) {
                    header(AuthHeader.AUTH_HEADER_KEY, AuthHeader.BEARER.plus(accessToken))
                }
            }

            //Authenticator
            install(Auth) {
                bearer {
                    refreshTokens {
                        val refreshTokenResponse = client.get {
                            url(Endpoints.REFRESH_TOKEN)
                            contentType(ContentType.Application.FormUrlEncoded)
                            setBody(
                                RefreshTokenRequest(
                                    grantType = CoreNetworkBuildKonfig.GRANT_TYPE,
                                    clientId = CoreNetworkBuildKonfig.CLIENT_ID,
                                    clientSecret = CoreNetworkBuildKonfig.CLIENT_SECRET
                                )
                            )
                            markAsRefreshTokenRequest()
                        }.body<RefreshTokenResponse?>()

                        val accessToken = refreshTokenResponse?.accessToken

                        if (accessToken.isNullOrBlank().not()) {
                            runBlocking { preferenceUtilApi.setAccessToken(accessToken!!) }

                            BearerTokens(
                                accessToken = accessToken!!,
                                refreshToken = accessToken!!
                            )
                        } else {
                            runBlocking { preferenceUtilApi.clearAccessToken() }
                            null
                        }
                    }
                }
            }

            //Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = NetworkApiConfig.REQUEST_TIMEOUT_MILLIS
                socketTimeoutMillis = NetworkApiConfig.SOCKET_TIMEOUT_MILLIS
                connectTimeoutMillis = NetworkApiConfig.CONNECT_TIMEOUT_MILLIS
            }

            //Logging
            if (shouldEnableLogging) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            loggerApi.logDWithTag(
                                tag = NetworkApiConfig.DEFAULT_LOG_TAG,
                                message = message
                            )
                        }
                    }
                    level = LogLevel.ALL
                }
            }

            //Response Observer
            if (shouldEnableLogging) {
                install(ResponseObserver) {
                    onResponse {
                        loggerApi.logDWithTag(
                            tag = NetworkApiConfig.DEFAULT_LOG_TAG,
                            message = it.bodyAsText()
                        )
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