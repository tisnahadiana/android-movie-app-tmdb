package com.deeromptech.androidmovieapptmdb.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.deeromptech.androidmovieapptmdb.data.mapper.toDomain
import com.deeromptech.androidmovieapptmdb.data.remote.api.TmdbApi
import com.deeromptech.androidmovieapptmdb.domain.model.Movie

class MoviePagingSource(
    private val api: TmdbApi,
    private val genreId: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX

            val response = api.discoverMoviesByGenre(
                genreId = genreId,
                page = page
            )

            val movies = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page >= response.totalPages || movies.isEmpty()) null else page + 1
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}