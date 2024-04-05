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

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Book;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.BookListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();

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
        bookAdapter = new BookAdapter(books, this);
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this, bookAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(bookAdapter);
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
        fetchBooks();
    }

    private void fetchBooks()
    {

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://beac-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;
        Call<BookListResponse> call = apiService.getBooks(authorizationHeader);
        call.enqueue(new Callback<BookListResponse>()
        {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response)
            {
                handleBookListResponse(response);
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleBookListResponse(Response<BookListResponse> response)
    {
        if (response.isSuccessful() && response.body() != null)
        {
            BookListResponse bookListResponse = response.body();
            if (bookListResponse.isSuccess())
            {
                List<Book> books = bookListResponse.getBooks();
                if (books != null && !books.isEmpty())
                {
                    bookAdapter.setBooks(books);
                } else
                {
                    Toast.makeText(MainActivity.this, "No books available.", Toast.LENGTH_SHORT).show();
                }
            } else
            {
                Toast.makeText(MainActivity.this, "Error: " + bookListResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }


}