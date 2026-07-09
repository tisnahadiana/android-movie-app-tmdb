package com.deeromptech.androidmovieapptmdb.core.common

import java.io.IOException
import retrofit2.HttpException

object ErrorMessageMapper {

    fun map(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> "No internet connection. Please check your network and try again."
            is HttpException -> {
                when (throwable.code()) {
                    401 -> "Invalid API token. Please check your TMDB access token."
                    404 -> "Data not found."
                    429 -> "Too many requests. Please try again later."
                    in 500..599 -> "Server error. Please try again later."
                    else -> "Something went wrong. Please try again."
                }
            }
            else -> throwable.message ?: "Something went wrong. Please try again."
        }
    }
}