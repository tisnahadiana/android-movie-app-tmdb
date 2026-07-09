package com.deeromptech.androidmovieapptmdb.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.deeromptech.androidmovieapptmdb.data.mapper.toDomain
import com.deeromptech.androidmovieapptmdb.data.remote.api.TmdbApi
import com.deeromptech.androidmovieapptmdb.domain.model.Review

class ReviewPagingSource(
    private val api: TmdbApi,
    private val movieId: Int
) : PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX

            val response = api.getMovieReviews(
                movieId = movieId,
                page = page
            )

            val reviews = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = reviews,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page >= response.totalPages || reviews.isEmpty()) null else page + 1
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}