package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieListResponse extends BasicResponse
{
    @Json(name = "data") private List<Movie> movies;

    public List<Movie> getMovies()
    {
        return movies;
    }
}
