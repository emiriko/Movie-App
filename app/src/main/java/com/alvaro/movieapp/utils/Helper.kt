package com.alvaro.movieapp.utils

import com.alvaro.movieapp.core.domain.model.Cast
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review

object Helper {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                id = 1022789,
                overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
                title = "Inside Out 2",
                genres = listOf("Animation", "Family", "Adventure", "Comedy"),
                image = "/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
                releaseDate = "2024-06-11",
                voteAverage = 7.684,
                runtime = 120,
                casts = listOf(
                    Cast(
                        name = "Amy Poehler",
                        image = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                    ),
                    Cast(
                        name = "Phyllis Smith",
                        image = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                    ),
                ),
                backdropImage = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 519182,
                overview = "Gru and Lucy and their girls — Margo, Edith and Agnes — welcome a new member to the Gru family, Gru Jr., who is intent on tormenting his dad. Meanwhile, Gru faces a new nemesis in Maxime Le Mal and his femme fatale girlfriend Valentina, forcing the family to go on the run.",
                title = "Despicable Me 4",
                genres = listOf("Animation", "Family", "Comedy", "Action"),
                image = "/3w84hCFJATpiCO5g8hpdWVPBbmq.jpg",
                releaseDate = "2024-06-20",
                voteAverage = 7.376,
                runtime = 120,
                casts = listOf(
                    Cast(
                        name = "Amy Poehler",
                        image = "Joy",
                    ),
                    Cast(
                        name = "Phyllis Smith",
                        image = "Sadness",
                    ),
                ),
                backdropImage = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                isFavorite = false,
            ),
            Movie(
                id = 748783,
                overview = "Garfield, the world-famous, Monday-hating, lasagna-loving indoor cat, is about to have a wild outdoor adventure! After an unexpected reunion with his long-lost father – scruffy street cat Vic – Garfield and his canine friend Odie are forced from their perfectly pampered life into joining Vic in a hilarious, high-stakes heist.",
                title = "The Garfield Movie",
                genres = listOf("Animation", "Comedy", "Family", "Adventure"),
                image = "/p6AbOJvMQhBmffd0PIv0u8ghWeY.jpg",
                releaseDate = "2024-04-30",
                voteAverage = 7.277,
                runtime = 120,
                casts = listOf(
                    Cast(
                        name = "Amy Poehler",
                        image = "Joy",
                    ),
                    Cast(
                        name = "Phyllis Smith",
                        image = "Sadness",
                    ),
                ),
                backdropImage = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                isFavorite = false,
            ),
        )
    }
    
    fun getReviews(): List<Review> {
        return listOf(
            Review(
                subject = "Alvaro Austin",
                review = "From DC Comics comes the Suicide Squad, " +
                        "an antihero team of incarcerated" +
                        " supervillains who act as deniable assets" +
                        " for the United States government.",
                image = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                rating = 9.5
            ),
            Review(
                subject = "Hank Schrader",
                review = "From DC Comics comes the Suicide Squad, \" +\n" +
                        "                        \"an antihero team of incarcerated\" +\n" +
                        "                        \" supervillains who act as deniable assets\" +\n" +
                        "                        \" for the United States government.",
                image = "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                rating = 3.0
            )
        )
    }
}