package com.alvaro.movieapp.core.utils

import com.alvaro.movieapp.core.data.source.local.entity.CastEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieWithCasts
import com.alvaro.movieapp.core.data.source.remote.response.MovieDetailResponse
import com.alvaro.movieapp.core.data.source.remote.response.MovieItem
import com.alvaro.movieapp.core.data.source.remote.response.ReviewItem
import com.alvaro.movieapp.core.domain.model.Cast
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review


object DataMapper{
    fun movieResponsesToMovieEntity(movieItems: List<MovieItem>): List<MovieEntity> {
        return movieItems.map { response ->
            MovieEntity(
                movieId = response.id,
                overview = response.overview,
                title = response.title,
                genres = response.genreIds.map { genreId -> GenreMapper.getGenreNameById(genreId) },
                image = response.posterPath ?: "",
                releaseDate = response.releaseDate ?: "Not Specified",
                voteAverage = response.voteAverage ?: 0.0,
            )
        }
    }

    fun movieEntitiesToMovieDomain(movieEntities: List<MovieEntity>): List<Movie> {
        return movieEntities.map { entity ->
            Movie(
                id = entity.movieId,
                overview = entity.overview,
                title = entity.title,
                genres = entity.genres,
                image = entity.image,
                releaseDate = entity.releaseDate,
                voteAverage = entity.voteAverage,
                runtime = entity.runtime,
                backdropImage = entity.backdropImage,
                isFavorite = entity.isFavorite,
                casts = emptyList()
            )
        }
    }
    
    fun movieWithCastsToMovieDomain(movieWithCasts: MovieWithCasts): Movie {
        val entity = movieWithCasts.movie
        val casts = movieWithCasts.casts 
        return Movie(
            id = entity.movieId,
            overview = entity.overview,
            title = entity.title,
            genres = entity.genres,
            image = entity.image,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage,
            runtime = entity.runtime,
            backdropImage = entity.backdropImage,
            isFavorite = entity.isFavorite,
            casts = casts.map { cast -> 
                Cast(
                    name = cast.actorName,
                    image = cast.actorImage
                )
            }
        )
    }
    
    fun movieDetailResponseToMovieEntity(response: MovieDetailResponse): MovieEntity {
        return MovieEntity(
            movieId = response.id,
            overview = response.overview ?: "No Information Provided",
            title = response.title ?: "No Information Provided",
            genres = response.genres.map { genre -> GenreMapper.getGenreNameById(genre.id) },
            image = response.posterPath ?: "",
            releaseDate = response.releaseDate ?: "No Information Provided",
            voteAverage = response.voteAverage ?: 0.0,
            runtime = response.runtime ?: 0,
            backdropImage = response.backdropPath ?: "",
        )
    }
    
    fun movieDetailResponseToCastEntity(response: MovieDetailResponse): List<CastEntity> {
        return (response.credits?.cast ?: emptyList()).map { cast -> 
            CastEntity(
                castId = cast.castId,
                movieId = response.id,
                actorImage = cast.profilePath ?: "",
                actorName = cast.name
            )
        }
    }
    
    fun movieReviewResponseToReviewDomain(response: List<ReviewItem>) : List<Review> {
        return response.map { review -> 
            Review(
                review = review.content,
                subject = review.author,
                rating = review.authorDetails.rating?.toDouble() ?: 0.0,
                image = review.authorDetails.avatarPath ?: "",
            )
        }
    }
    
    fun movieDomainToMovieEntity(movieListItem: Movie): MovieEntity {
        return MovieEntity(
            movieId = movieListItem.id,
            overview = movieListItem.overview,
            title = movieListItem.title,
            genres = movieListItem.genres,
            image = movieListItem.image,
            releaseDate = movieListItem.releaseDate,
            voteAverage = movieListItem.voteAverage,
            runtime = movieListItem.runtime,
            backdropImage = movieListItem.backdropImage,
            isFavorite = movieListItem.isFavorite,
        )
    }
}