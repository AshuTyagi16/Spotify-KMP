package com.spotify.app.core_preferences.shared.api

/**
 * Multiplatform Preference API
 * */
interface PreferenceApi {

    // *** String Functions *** //

    /**
     * Function to get [String] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getString(key: String): String?

    /**
     * Function to get [String] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getString(key: String, default: String): String

    /**
     * Function to put [String] value in preferences
     * */
    suspend fun putString(key: String, value: String)

    // *** Int Functions *** //

    /**
     * Function to get [Int] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getInt(key: String): Int?

    /**
     * Function to get [Int] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getInt(key: String, default: Int): Int

    /**
     * Function to put [Int] value in preferences
     * */
    suspend fun putInt(key: String, value: Int)


    // *** Long Functions *** //
    /**
     * Function to get [Long] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getLong(key: String): Long?

    /**
     * Function to get [Long] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getLong(key: String, default: Long): Long

    /**
     * Function to put [Long] value in preferences
     * */
    suspend fun putLong(key: String, value: Long)

    // *** Float Functions *** //

    /**
     * Function to get [Float] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getFloat(key: String): Float?

    /**
     * Function to get [Float] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getFloat(key: String, default: Float): Float

    /**
     * Function to put [Float] value in preferences
     * */
    suspend fun putFloat(key: String, value: Float)

    // *** Double Functions *** //

    /**
     * Function to get [Double] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getDouble(key: String): Double?

    /**
     * Function to get [Double] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getDouble(key: String, default: Double): Double

    /**
     * Function to put [Double] value in preferences
     * */
    suspend fun putDouble(key: String, value: Double)


    // *** Boolean Function *** //

    /**
     * Function to get [Boolean] value from preferences
     * Default value will be null if key is not found
     * */
    suspend fun getBoolean(key: String): Boolean?

    /**
     * Function to get [Boolean] value from preferences
     * Default value will be [default] key
     * */
    suspend fun getBoolean(key: String, default: Boolean): Boolean

    /**
     * Function to put [Boolean] value in preferences
     * */
    suspend fun putBoolean(key: String, value: Boolean)

    /**
     * Function to clear all the values in the preferences
     * */
    suspend fun clearAll()
}