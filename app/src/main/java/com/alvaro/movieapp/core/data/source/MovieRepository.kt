package com.alvaro.movieapp.core.data.source

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvaro.movieapp.core.data.source.local.LocalDataSource
import com.alvaro.movieapp.core.data.source.local.entity.MovieEntity
import com.alvaro.movieapp.core.data.source.remote.RemoteDataSource
import com.alvaro.movieapp.core.data.source.remote.response.MovieDetailResponse
import com.alvaro.movieapp.core.data.source.remote.response.MovieItem
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.domain.repository.IMovieRepository
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IMovieRepository {
    private suspend fun getMovies(
        createCall: suspend () -> Flow<Resource<List<MovieItem>>>,
        saveCallResult: suspend (List<MovieItem>) -> Unit,
    ): Flow<Resource<List<Movie>>> = object : NetworkBoundResource<List<MovieItem>, List<Movie>>() {
        override fun loadFromDB(): Flow<List<Movie>> {
            return localDataSource.getAllMovies().mapLatest { DataMapper.movieEntitiesToMovieDomain(it) }
        }

        override suspend fun createCall(): Flow<Resource<List<MovieItem>>> =
            createCall()

        override fun useDb(): Boolean = false

        override fun List<MovieItem>.mapResponseToDomain(): List<Movie> {
            val entities = DataMapper.movieResponsesToMovieEntity(this)
            return DataMapper.movieEntitiesToMovieDomain(entities)
        }

        override suspend fun saveCallResult(data: List<MovieItem>) = saveCallResult(data)

        override fun shouldFetch(data: List<Movie>?): Boolean = true // Always fetch from remote
    }.asFlow()

    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, QueryType.NOW_PLAYING) }
        ).flow
    }

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, QueryType.POPULAR) }
        ).flow
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, QueryType.TOP_RATED) }
        ).flow
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, QueryType.UPCOMING) }
        ).flow
    }

    override suspend fun getMovieDetail(id: Int): Flow<Resource<Movie>> = object : NetworkBoundResource<MovieDetailResponse, Movie>() {
        override fun loadFromDB(): Flow<Movie> {
            return localDataSource.getMovieById(id).mapLatest { movie ->
                if ( movie != null ) {
                    DataMapper.movieWithCastsToMovieDomain(movie)
                } else {
                    Movie(
                        id = 0,
                        overview = "",
                        title = "",
                        genres = emptyList(),
                        image = "",
                        releaseDate = "",
                        voteAverage = 0.0,
                        runtime = 0,
                        backdropImage = "",
                        isFavorite = false,
                        casts = emptyList()
                    )
                }
            }
        }

        override suspend fun createCall(): Flow<Resource<MovieDetailResponse>> {
           return remoteDataSource.getMovieDetail(id)
        }

        override fun useDb(): Boolean = true

        override fun MovieDetailResponse.mapResponseToDomain(): Movie {
            return Movie(
                id = id,
                overview = overview ?: "No Information Provided",
                title = title ?: "No Information Provided",
                genres = emptyList(),
                image = posterPath ?: "",
                releaseDate = releaseDate ?: "No Information Provided",
                voteAverage = voteAverage ?: 0.0,
                runtime = runtime ?: 0,
                backdropImage = backdropPath ?: "",
                isFavorite = false,
                casts = emptyList()
            )
        }

        override suspend fun saveCallResult(data: MovieDetailResponse) {
            var currentMovieEntity = localDataSource.getMovieEntityById(id).first()
            
            if (currentMovieEntity == null) {
                currentMovieEntity = MovieEntity(
                    movieId = id,
                    overview = data.overview ?: "No Information Provided",
                    title = data.title ?: "No Information Provided",
                    genres = data.genres.map { genre -> genre.name },
                    image = data.posterPath ?: "",
                    releaseDate = data.releaseDate ?: "No Information Provided",
                    voteAverage = data.voteAverage ?: 0.0,
                    runtime = data.runtime ?: 0,
                    backdropImage = data.backdropPath ?: "",
                    isFavorite = false
                )
            }
            
            val movieEntity = DataMapper.movieDetailResponseToMovieEntity(data)
            val castEntity = DataMapper.movieDetailResponseToCastEntity(data)
            
            val finalEntity = movieEntity.copy(
                isFavorite = currentMovieEntity.isFavorite
            )
            
            localDataSource.insertMovie(finalEntity)
            localDataSource.insertCasts(castEntity)
        }

        override fun shouldFetch(data: Movie?): Boolean = true
    }.asFlow()

    override suspend fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>> =
        flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getMovieReviews(movieId).first()) {
                is Resource.Success -> {
                    emit(Resource.Success(DataMapper.movieReviewResponseToReviewDomain(response.data)))
                }
                is Resource.Error -> {
                    emit(Resource.Error(response.error))
                }
                else -> Unit
            }
        }

    override suspend fun getFavoritedMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            localDataSource.getFavoriteMovies().collect { data ->
                emit(Resource.Success(DataMapper.movieEntitiesToMovieDomain(data)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun getSearchResult(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, QueryType.SEARCH, query) }
        ).flow
    }
    
    override suspend fun updateMovieState(movie: Movie, newState: Boolean) {
        val movieEntity = DataMapper.movieDomainToMovieEntity(movie)
        localDataSource.setFavoriteMovie(movieEntity, newState)
    }
}