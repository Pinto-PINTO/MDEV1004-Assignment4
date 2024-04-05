// File Name: MainActivity.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Movie;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.MovieListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        setupRecyclerView();

        findViewById(R.id.addNewButton).setOnClickListener(view ->
        {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.logoutButton).setOnClickListener(view -> logout());
    }

    private void setupRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(movies, this);
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this, movieAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(movieAdapter);
    }

    private void logout()
    {
        clearSharedPreferences();
        goToLoginActivity();
    }

    private void goToLoginActivity()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearSharedPreferences()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        fetchMovies();
    }

    private void fetchMovies()
    {

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://9bea-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;
        Call<MovieListResponse> call = apiService.getMovies(authorizationHeader);
        call.enqueue(new Callback<MovieListResponse>()
        {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response)
            {
                handleMovieListResponse(response);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleMovieListResponse(Response<MovieListResponse> response)
    {
        if (response.isSuccessful() && response.body() != null)
        {
            MovieListResponse movieListResponse = response.body();
            if (movieListResponse.isSuccess())
            {
                List<Movie> movies = movieListResponse.getMovies();
                if (movies != null && !movies.isEmpty())
                {
                    movieAdapter.setMovies(movies);
                } else
                {
                    Toast.makeText(MainActivity.this, "No movies available.", Toast.LENGTH_SHORT).show();
                }
            } else
            {
                Toast.makeText(MainActivity.this, "Error: " + movieListResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }


}