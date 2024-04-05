package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable
{
    @Json(name = "_id")
    private String id;
    @Json(name = "movieID")
    private String movieID;
    @Json(name = "title")
    private String title;
    @Json(name = "studio")
    private String studio;
    @Json(name = "genres")
    private List<String> genres;
    @Json(name = "directors")
    private List<String> directors;
    @Json(name = "writers")
    private List<String> writers;
    @Json(name = "actors")
    private List<String> actors;
    @Json(name = "year")
    private int year;
    @Json(name = "length")
    private int length;
    @Json(name = "shortDescription")
    private String shortDescription;
    @Json(name = "mpaRating")
    private String mpaRating;
    @Json(name = "criticsRating")
    private double criticsRating;
    @Json(name = "posterlink")
    private String posterLink;

    public Movie(String id, String movieID, String title, String studio, List<String> genres, List<String> directors, List<String> writers, List<String> actors, int year, int length, String shortDescription, String mpaRating, double criticsRating, String posterLink)
    {
        this.id = id;
        this.movieID = movieID;
        this.title = title;
        this.studio = studio;
        this.genres = genres;
        this.directors = directors;
        this.writers = writers;
        this.actors = actors;
        this.year = year;
        this.length = length;
        this.shortDescription = shortDescription;
        this.mpaRating = mpaRating;
        this.criticsRating = criticsRating;
        this.posterLink = posterLink;
    }

    public String getId()
    {
        return id;
    }

    public String getMovieID()
    {
        return movieID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getStudio()
    {
        return studio;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public List<String> getDirectors()
    {
        return directors;
    }

    public List<String> getWriters()
    {
        return writers;
    }

    public List<String> getActors()
    {
        return actors;
    }

    public int getYear()
    {
        return year;
    }

    public int getLength()
    {
        return length;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getMpaRating()
    {
        return mpaRating;
    }

    public double getCriticsRating()
    {
        return criticsRating;
    }

    public String getPosterLink()
    {
        return posterLink;
    }
}

