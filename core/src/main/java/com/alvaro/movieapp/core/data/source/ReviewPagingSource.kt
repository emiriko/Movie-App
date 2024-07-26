package com.alvaro.movieapp.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvaro.movieapp.core.data.source.remote.RemoteDataSource
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.core.utils.DataMapper
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val movieId: Int
) : PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1
        return try {
            val response = remoteDataSource.getMovieReviews(movieId, page).first()

            if (response is Resource.Success) {
                val reviews = response.data
                LoadResult.Page(
                    data = DataMapper.movieReviewResponseToReviewDomain(reviews),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (reviews.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("Error fetching movies"))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}