package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class GenreResponseDto(
    val genres: List<GenreDto> = emptyList()
)

data class GenreDto(
    val id: Int,
    val name: String?
)