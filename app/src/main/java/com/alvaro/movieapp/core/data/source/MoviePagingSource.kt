package com.alvaro.movieapp.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvaro.movieapp.core.data.source.remote.RemoteDataSource
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.core.utils.DataMapper
import kotlinx.coroutines.flow.first

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val queryType: QueryType
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = when (queryType) {
                QueryType.NOW_PLAYING -> remoteDataSource.getNowPlayingMovies(page)
                QueryType.POPULAR -> remoteDataSource.getPopularMovies(page)
                QueryType.TOP_RATED -> remoteDataSource.getTopRatedMovies(page)
                QueryType.UPCOMING -> remoteDataSource.getUpcomingMovies(page)
            }.first()

            if (response is Resource.Success) {
                val movies = response.data
                val movieEntities = DataMapper.movieResponsesToMovieEntity(movies)
                LoadResult.Page(
                    data = DataMapper.movieEntitiesToMovieDomain(movieEntities),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movies.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("Error fetching movies"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

enum class QueryType {
    NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
}