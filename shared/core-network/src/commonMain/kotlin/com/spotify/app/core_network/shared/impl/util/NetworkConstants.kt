package com.spotify.app.core_network.shared.impl.util

object NetworkConstants {
    internal object Endpoints {
        const val REFRESH_TOKEN = "/api/token"
    }

    object NetworkErrorCodes {
        const val ACCESS_TOKEN_EXPIRED = 401
        const val REFRESH_TOKEN_EXPIRED = 403
        const val INTERNET_NOT_WORKING = 712
        const val UNKNOWN_ERROR_OCCURRED = 713
        const val NETWORK_CALL_CANCELLED = 714
        const val DATA_SERIALIZATION_ERROR = 715
    }

    object NetworkErrorMessages {
        const val SOME_ERROR_OCCURRED = "Some error occurred"
        const val ACCESS_TOKEN_EXPIRED = ""
        const val PLEASE_LOGIN_AGAIN = "Please login again"
        const val APP_UNDER_MAINTENANCE = "App under maintenance"
        const val PLEASE_CHECK_YOUR_INTERNET_CONNECTION = "Please check your internet connection"
        const val DATA_SERIALIZATION_ERROR = "Data serialization error"
    }

    internal object NetworkApiConfig {
        const val DEFAULT_LOG_TAG = "##NETWORK_LOG##"

        const val SOCKET_TIMEOUT_MILLIS = 60_000L
        const val CONNECT_TIMEOUT_MILLIS = 60_000L
        const val REQUEST_TIMEOUT_MILLIS = 60_000L
    }

    internal object AuthHeader {
        const val AUTH_HEADER_KEY = "Authorization"
        const val BEARER = "Bearer "
    }


}