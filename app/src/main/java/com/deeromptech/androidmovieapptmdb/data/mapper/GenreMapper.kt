package com.deeromptech.androidmovieapptmdb.data.mapper

import com.deeromptech.androidmovieapptmdb.data.remote.dto.GenreDto
import com.deeromptech.androidmovieapptmdb.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name.orEmpty()
    )
}