package com.spotify.app.core_network.shared.impl.util

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadResponse

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

//fun <T> Flow<StoreReadResponse<RestClientResult<T>>>.mapWrappedStoreResponseToRestClientResult(): Flow<RestClientResult<T>> =
//    this.map {
//        when (it) {
//            is StoreReadResponse.Data -> {
//                when (it.value.status) {
//                    RestClientResult.Status.SUCCESS -> {
//                        RestClientResult.success(it.value.data!!)
//                    }
//
//                    RestClientResult.Status.ERROR -> {
//                        RestClientResult.error(
//                            errorMessage = it.value.errorMessage.orEmpty(),
//                            errorCode = it.value.errorCode
//                        )
//                    }
//
//                    RestClientResult.Status.LOADING -> {
//                        RestClientResult.loading()
//                    }
//
//                    RestClientResult.Status.IDLE -> {
//                        RestClientResult.idle()
//                    }
//                }
//            }
//
//            is StoreReadResponse.Error.Exception -> {
//                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
//            }
//
//            is StoreReadResponse.Error.Message -> {
//                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
//            }
//
//            is StoreReadResponse.Loading -> {
//                RestClientResult.loading()
//            }
//
//            is StoreReadResponse.NoNewData -> {
//                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
//            }
//        }
//    }

fun <T> Flow<StoreReadResponse<T>>.mapStoreResponseToRestClientResult(): Flow<RestClientResult<T>> =
    this.map {
        when (it) {
            is StoreReadResponse.Data -> {
                RestClientResult.success(it.value)
            }

            is StoreReadResponse.Error.Exception -> {
                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
            }

            is StoreReadResponse.Error.Message -> {
                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
            }

            is StoreReadResponse.Loading -> {
                RestClientResult.loading()
            }

            is StoreReadResponse.NoNewData -> {
                RestClientResult.error(errorMessage = it.errorMessageOrNull().orEmpty())
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