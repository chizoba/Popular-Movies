package com.github.chizoba.popularmovies;

/**
 * Created by Chizoba on 4/4/2017.
 */

public class Movie {
    String moviePoster;
    String originalTitle;
    String plotSynopsis;
    Double userRating;
    String releaseDate;

    public Movie(String moviePoster, String originalTitle, String plotSynopsis, Double userRating, String releaseDate) {
        this.moviePoster = moviePoster;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }
}
