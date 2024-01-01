package com.spotify.app.core_preferences.shared.impl

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import com.spotify.app.core_preferences.shared.api.PreferenceApi

@OptIn(ExperimentalSettingsApi::class)
internal class PreferenceApiImpl(
    private val suspendSettings: SuspendSettings,
) : PreferenceApi {

    override suspend fun getString(key: String): String? {
        return suspendSettings.getStringOrNull(key)
    }

    override suspend fun getString(key: String, default: String): String {
        return suspendSettings.getString(key, default)
    }

    override suspend fun putString(key: String, value: String) {
        suspendSettings.putString(key, value)
    }

    override suspend fun getInt(key: String): Int? {
        return suspendSettings.getIntOrNull(key)
    }

    override suspend fun getInt(key: String, default: Int): Int {
        return suspendSettings.getInt(key, default)
    }


    override suspend fun putInt(key: String, value: Int) {
        suspendSettings.putInt(key, value)
    }


    override suspend fun getLong(key: String): Long? {
        return suspendSettings.getLongOrNull(key)
    }


    override suspend fun getLong(key: String, default: Long): Long {
        return suspendSettings.getLong(key, default)
    }


    override suspend fun putLong(key: String, value: Long) {
        suspendSettings.putLong(key, value)
    }

    override suspend fun getFloat(key: String): Float? {
        return suspendSettings.getFloatOrNull(key)
    }


    override suspend fun getFloat(key: String, default: Float): Float {
        return suspendSettings.getFloat(key, default)
    }


    override suspend fun putFloat(key: String, value: Float) {
        suspendSettings.putFloat(key, value)
    }

    override suspend fun getDouble(key: String): Double? {
        return suspendSettings.getDoubleOrNull(key)
    }


    override suspend fun getDouble(key: String, default: Double): Double {
        return suspendSettings.getDouble(key, default)
    }


    override suspend fun putDouble(key: String, value: Double) {
        suspendSettings.putDouble(key, value)
    }


    override suspend fun getBoolean(key: String): Boolean? {
        return suspendSettings.getBooleanOrNull(key)
    }


    override suspend fun getBoolean(key: String, default: Boolean): Boolean {
        return suspendSettings.getBoolean(key, default)
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        suspendSettings.putBoolean(key, value)
    }

    override suspend fun clearAll() {
        suspendSettings.clear()
    }
}