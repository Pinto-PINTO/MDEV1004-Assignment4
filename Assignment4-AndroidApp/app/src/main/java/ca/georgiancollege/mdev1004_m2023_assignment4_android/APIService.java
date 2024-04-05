// File Name: APIService.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.BasicResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.LoginRequest;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.LoginResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Book;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.BookListResponse;
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
    Call<BookListResponse> getBooks(@Header("Authorization") String authToken);

    @POST("/api/add")
    Call<Void> addBook(@Header("Authorization") String authToken, @Body Book book);

    @PUT("/api/update/{bookId}")
    Call<Void> updateBook(@Header("Authorization") String authToken, @Path("bookId") String bookId, @Body Book book);


    @DELETE("/api/delete/{bookID}")
    Call<Void> deleteBook(@Header("Authorization") String authToken, @Path("bookID") String bookID);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


    @POST("/api/register")
    Call<BasicResponse> registerUser(@Body RegisterRequest user);

}
