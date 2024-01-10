package com.spotify.app.core_network.shared.impl.util

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend inline fun <DTO, Domain> RestClientResult<DTO>.mapFromDTO(
    crossinline dataMapper: suspend (t: DTO?) -> Domain
): RestClientResult<Domain> {
    return when (status) {
        RestClientResult.Status.IDLE -> {
            RestClientResult.idle()
        }

        RestClientResult.Status.LOADING -> {
            RestClientResult.loading()
        }

        RestClientResult.Status.SUCCESS -> {
            RestClientResult.success(data = dataMapper.invoke(data))
        }

        RestClientResult.Status.ERROR -> {
            RestClientResult.error(errorMessage = errorMessage.orEmpty(), errorCode = errorCode)
        }
    }
}

suspend fun <T> Flow<RestClientResult<T>>.collect(
    onLoading: suspend () -> Unit,
    onSuccess: suspend (data: T) -> Unit,
    onError: suspend (errorMessage: String, errorCode: Int?) -> Unit
) {
    this.collectLatest {
        when (it.status) {
            RestClientResult.Status.IDLE -> {
                // Do nothing
            }

            RestClientResult.Status.LOADING -> {
                onLoading.invoke()
            }

            RestClientResult.Status.SUCCESS -> {
                onSuccess.invoke(it.data!!)
            }

            RestClientResult.Status.ERROR -> {
                onError.invoke(it.errorMessage.orEmpty(), it.errorCode)
            }
        }
    }
}