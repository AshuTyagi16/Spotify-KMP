package com.spotify.app.core_network.shared.impl.data.base

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.NetworkErrorMessages
import com.spotify.app.core_network.shared.impl.util.NetworkConstants.NetworkErrorCodes
import com.spotify.app.core_network.shared.impl.util.NetworkApiEvent
import com.spotify.app.core_network.shared.impl.util.NetworkEventBus
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException

abstract class BaseDataSource {

    protected suspend inline fun <reified T> getResult(call: () -> HttpResponse): RestClientResult<T> {
        val result: HttpResponse?
        return try {
            result = call()
            if (result.status == HttpStatusCode.OK) {
                val data: T = result.body()
                RestClientResult.success(data)
            } else {
                RestClientResult.error(
                    errorMessage = NetworkErrorMessages.SOME_ERROR_OCCURRED,
                    errorCode = result.status.value
                )
            }
        } catch (e: ClientRequestException) {
            return when (val statusCode = e.response.status.value) {
                NetworkErrorCodes.ACCESS_TOKEN_EXPIRED -> {
                    RestClientResult.error(
                        errorMessage = NetworkErrorMessages.ACCESS_TOKEN_EXPIRED,
                        errorCode = statusCode
                    )
                }

                NetworkErrorCodes.REFRESH_TOKEN_EXPIRED -> {
                    NetworkEventBus.INSTANCE.invokeEvent(NetworkApiEvent.REFRESH_TOKEN_EXPIRED)
                    RestClientResult.error(
                        errorMessage = NetworkErrorMessages.PLEASE_LOGIN_AGAIN,
                        errorCode = statusCode
                    )
                }

                else -> {
                    RestClientResult.error(
                        errorMessage = e.message,
                        errorCode = NetworkErrorCodes.UNKNOWN_ERROR_OCCURRED
                    )
                }
            }
        } catch (e: ServerResponseException) {
            val statusCode = e.response.status.value
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.APP_UNDER_MAINTENANCE,
                errorCode = statusCode
            )
        } catch (e: IOException) {
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: UnresolvedAddressException) {
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: SocketTimeoutException) {
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: SerializationException) {
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.DATA_SERIALIZATION_ERROR,
                errorCode = NetworkErrorCodes.DATA_SERIALIZATION_ERROR,
            )
        } catch (e: JsonConvertException) {
            RestClientResult.error(
                errorMessage = NetworkErrorMessages.DATA_SERIALIZATION_ERROR,
                errorCode = NetworkErrorCodes.DATA_SERIALIZATION_ERROR
            )
        } catch (e: CancellationException) {
            RestClientResult.error(
                errorMessage = "",  //This is a special case in which we don't want to show any error
                errorCode = NetworkErrorCodes.NETWORK_CALL_CANCELLED
            )
        } catch (e: Exception) {
            RestClientResult.error(
                errorMessage = e.message ?: NetworkErrorMessages.SOME_ERROR_OCCURRED,
                errorCode = NetworkErrorCodes.UNKNOWN_ERROR_OCCURRED
            )
        }
    }

    protected suspend fun <T> retryIOs(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 100,
        maxDelay: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T,
        shouldRetry: (result: T) -> Boolean
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            val result = block()
            if (shouldRetry(result).not()) {
                return result
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block()
    }
}