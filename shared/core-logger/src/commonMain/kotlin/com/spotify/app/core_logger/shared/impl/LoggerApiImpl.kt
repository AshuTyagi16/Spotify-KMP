package com.spotify.app.core_logger.shared.impl

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import com.spotify.app.core_logger.shared.api.LoggerApi

internal class LoggerApiImpl(
    private val shouldEnableLogs: Boolean
) : LoggerApi {

    init {
        Logger.setLogWriters(platformLogWriter())
    }

    override fun logD(message: String) {
        if (shouldEnableLogs)
            Logger.d(message)
    }

    override fun logDWithTag(tag: String, message: String) {
        if (shouldEnableLogs)
            Logger.withTag(tag).d(message)
    }

    override fun logE(message: String, e: Exception) {
        if (shouldEnableLogs)
            Logger.e(message, e)
    }

    override fun logEWithTag(tag: String, message: String, e: Exception) {
        if (shouldEnableLogs)
            Logger.withTag(tag).e(message, e)
    }
}