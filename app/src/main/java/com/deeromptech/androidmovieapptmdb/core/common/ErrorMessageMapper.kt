package com.deeromptech.androidmovieapptmdb.core.common

import com.squareup.moshi.JsonDataException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

object ErrorMessageMapper {

    fun map(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> {
                "No internet connection. Please check your network and try again."
            }

            is SocketTimeoutException -> {
                "Connection timeout. Please try again."
            }

            is IOException -> {
                "Network error. Please check your connection and try again."
            }

            is JsonDataException -> {
                "Failed to read movie data. Please try again."
            }

            is HttpException -> {
                when (throwable.code()) {
                    401 -> "Invalid API token. Please check your TMDB access token."
                    403 -> "Access denied. Please check your TMDB API permission."
                    404 -> "Data not found."
                    429 -> "Too many requests. Please try again later."
                    in 500..599 -> "Server error. Please try again later."
                    else -> "Something went wrong. Please try again."
                }
            }

            else -> {
                throwable.message ?: "Something went wrong. Please try again."
            }
        }
    }
}