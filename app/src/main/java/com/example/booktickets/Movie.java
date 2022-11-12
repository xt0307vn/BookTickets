package com.example.booktickets;

import java.util.List;

public class Movie {
    String movieName, movieKind, movieUrlImage, movieTimes;
    List<String> movieTimeStart;

    public Movie(String movieName, String movieKind, String movieUrlImage,String movieTimes) {
        this.movieName = movieName;
        this.movieKind = movieKind;
        this.movieUrlImage = movieUrlImage;
        this.movieTimes = movieTimes;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieKind() {
        return movieKind;
    }

    public void setMovieKind(String movieKind) {
        this.movieKind = movieKind;
    }

    public String getMovieUrlImage() {
        return movieUrlImage;
    }

    public void setMovieUrlImage(String movieUrlImage) {
        this.movieUrlImage = movieUrlImage;
    }

    public String getMovieTimes() {
        return movieTimes;
    }

    public void setMovieTimes(String movieTimes) {
        this.movieTimes = movieTimes;
    }

}
