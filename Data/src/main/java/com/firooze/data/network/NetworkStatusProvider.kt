package com.firooze.data.network

interface NetworkStatusProvider {
    fun isInternetAvailable(): Boolean
}