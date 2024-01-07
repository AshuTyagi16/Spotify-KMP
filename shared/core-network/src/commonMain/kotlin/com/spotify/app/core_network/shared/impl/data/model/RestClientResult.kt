package com.spotify.app.core_network.shared.impl.data.model

data class RestClientResult<out T>(
    val status: Status,
    val data: T? = null,
    val errorMessage: String? = null,
    val errorCode: Int? = null,
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        IDLE
    }

    companion object {
        fun <T> success(data: T): RestClientResult<T> {
            return RestClientResult(Status.SUCCESS, data)
        }

        fun <T> loading(): RestClientResult<T> {
            return RestClientResult(Status.LOADING)
        }

        fun <T> idle(): RestClientResult<T> {
            return RestClientResult(Status.IDLE)
        }

        fun <T> error(
            errorMessage: String,
            errorCode: Int? = null
        ): RestClientResult<T> {
            return RestClientResult(
                Status.ERROR,
                errorMessage = errorMessage,
                errorCode = errorCode
            )
        }
    }
}