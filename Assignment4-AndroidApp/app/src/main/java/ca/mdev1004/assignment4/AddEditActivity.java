// File Name: AddEditActivity.java
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024
package ca.mdev1004.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.UUID;

import ca.mdev1004.assignment4.R;
import ca.mdev1004.assignment4.models.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class AddEditActivity extends AppCompatActivity
{

    public static String EXTRA_BOOK_ID = "book";

    // Fields
    private EditText bookIDEditText;
    private EditText bookNameEditText;
    private EditText isbnEditText;
    private EditText ratingEditText;
    private EditText genreEditText;
    private EditText authorEditText;
    private EditText posterLinkEditText;
    private TextView headerTextView;
    private Book book;

    // To keep track if the book is being edited
    private boolean isEditingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // initialization
        bookIDEditText = findViewById(R.id.bookIDEditText);
        bookNameEditText = findViewById(R.id.bookNameEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        ratingEditText = findViewById(R.id.ratingEditText);
        genreEditText = findViewById(R.id.genreEditText);
        authorEditText = findViewById(R.id.authorEditText);
        posterLinkEditText = findViewById(R.id.posterLinkEditText);
        headerTextView = findViewById(R.id.headerTextView);

        // Check if in editing mode
        if (getIntent().hasExtra(EXTRA_BOOK_ID))
        {
            isEditingMode = true;
            this.book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK_ID);
            showBookDetails();
            headerTextView.setText("Edit Book");
        } else
        {
            headerTextView.setText("Add Book");
        }

        // Set click listeners for buttons
        findViewById(R.id.saveButton).setOnClickListener(v -> saveBook());

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
    }

    private void showBookDetails()
    {
        bookIDEditText.setText(book.getBookID());
        bookNameEditText.setText(book.getBookName());
        isbnEditText.setText(book.getIsbn());
        ratingEditText.setText(String.valueOf(book.getRating()));
        genreEditText.setText(String.valueOf(book.getGenre()));
        authorEditText.setText(book.getAuthor());
        posterLinkEditText.setText(book.getPosterLink());
    }

    private void saveBook()
    {
        String bookID = bookIDEditText.getText().toString().trim();
        String bookName = bookNameEditText.getText().toString().trim();
        String isbn = isbnEditText.getText().toString().trim();
        int rating = Integer.parseInt(ratingEditText.getText().toString().trim());
        String genre = genreEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String posterLink = posterLinkEditText.getText().toString().trim();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        if (bookID.isEmpty() || bookName.isEmpty() || isbn.isEmpty() || genre.isEmpty() ||
                author.isEmpty())
        {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // If book is being edited set current id or create random UUID
        Book requestBody = new Book(isEditingMode ? book.getId() : UUID.randomUUID().toString(), bookID, bookName,
                isbn, rating, genre,
                author, posterLink);

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://5bc5-2607-fea8-6521-9d00-4cbd-bf74-b068-767d.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        Call<Void> call;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;

        if (isEditingMode)
        {
            call = apiService.updateBook(authorizationHeader, book.getId(), requestBody);
        } else
        {
            call = apiService.addBook(authorizationHeader, requestBody);
        }

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                Log.d("ADD ", response.toString());
                if (response.isSuccessful())
                {
                    // Book updated or created successfully
                    finish();
                } else
                {
                    // Handle the error here (e.g., show a Toast)
                    Toast.makeText(AddEditActivity.this, "Failed to update book.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                // Handle the error here (e.g., show a Toast)
                Toast.makeText(AddEditActivity.this, "Failed to update book. Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}