package com.firooze.data

inline fun <T : Any, M : Any> apiCall(call: () -> T, map: (T) -> M): Result<M> {
    return try {
        Result.success(map(call()))

    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}