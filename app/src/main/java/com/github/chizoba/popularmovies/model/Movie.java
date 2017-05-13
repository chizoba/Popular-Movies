package com.github.chizoba.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chizoba on 4/4/2017.
 */

public class Movie implements Parcelable {
    public String moviePoster;
    public String originalTitle;
    public String plotSynopsis;
    public String userRating;
    public String releaseDate;
    public String id;

    public Movie(String moviePoster, String originalTitle, String plotSynopsis, String userRating, String releaseDate, String id) {
        this.moviePoster = moviePoster;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public Movie() {
        // Normal actions performed by class, since this is still a normal object!
    }

    private Movie(Parcel in) {
        moviePoster = in.readString();
        originalTitle = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(moviePoster);
        out.writeString(originalTitle);
        out.writeString(plotSynopsis);
        out.writeString(userRating);
        out.writeString(releaseDate);
        out.writeString(id);
    }
}
