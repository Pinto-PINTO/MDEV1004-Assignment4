// File Name: APIService.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.BasicResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.LoginRequest;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.LoginResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Movie;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.MovieListResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService
{


    @GET("/api/list")
    Call<MovieListResponse> getMovies(@Header("Authorization") String authToken);

    @POST("/api/add")
    Call<Void> addMovie(@Header("Authorization") String authToken, @Body Movie movie);

    @PUT("/api/update/{movieId}")
    Call<Void> updateMovie(@Header("Authorization") String authToken, @Path("movieId") String movieId, @Body Movie movie);


    @DELETE("/api/delete/{movieID}")
    Call<Void> deleteMovie(@Header("Authorization") String authToken, @Path("movieID") String movieID);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


    @POST("/api/register")
    Call<BasicResponse> registerUser(@Body RegisterRequest user);

}
