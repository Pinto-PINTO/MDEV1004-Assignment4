// File Name: RegisterActivity.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.UUID;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class AddEditActivity extends AppCompatActivity
{

    public static String EXTRA_MOVIE_ID = "movie";

    // Fields
    private EditText movieIDEditText;
    private EditText titleEditText;
    private EditText studioEditText;
    private EditText genresEditText;
    private EditText directorsEditText;
    private EditText writersEditText;
    private EditText actorsEditText;
    private EditText yearEditText;
    private EditText lengthEditText;
    private EditText shortDescriptionEditText;
    private EditText mpaRatingEditText;
    private EditText criticsRatingEditText;
    private EditText posterLinkEditText;
    private TextView headerTextView;
    private Movie movie;

    // To keep track if the movie is being edited
    private boolean isEditingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // initialization
        movieIDEditText = findViewById(R.id.movieIDEditText);
        titleEditText = findViewById(R.id.titleEditText);
        studioEditText = findViewById(R.id.studioEditText);
        genresEditText = findViewById(R.id.genresEditText);
        directorsEditText = findViewById(R.id.directorsEditText);
        writersEditText = findViewById(R.id.writersEditText);
        actorsEditText = findViewById(R.id.actorsEditText);
        yearEditText = findViewById(R.id.yearEditText);
        lengthEditText = findViewById(R.id.lengthEditText);
        shortDescriptionEditText = findViewById(R.id.shortDescriptionEditText);
        mpaRatingEditText = findViewById(R.id.mpaRatingEditText);
        criticsRatingEditText = findViewById(R.id.criticsRatingEditText);
        posterLinkEditText = findViewById(R.id.posterLinkEditText);
        headerTextView = findViewById(R.id.headerTextView);

        // Check if in editing mode
        if (getIntent().hasExtra(EXTRA_MOVIE_ID))
        {
            isEditingMode = true;
            this.movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);
            showMovieDetails();
            headerTextView.setText("Edit Movie");
        } else
        {
            headerTextView.setText("Add Movie");
        }

        // Set click listeners for buttons
        findViewById(R.id.saveButton).setOnClickListener(v -> saveMovie());

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
    }

    private void showMovieDetails()
    {
        movieIDEditText.setText(movie.getMovieID());
        titleEditText.setText(movie.getTitle());
        studioEditText.setText(movie.getStudio());
        genresEditText.setText(String.join(", ", movie.getGenres()));
        directorsEditText.setText(String.join(", ", movie.getDirectors()));
        writersEditText.setText(String.join(", ", movie.getWriters()));
        actorsEditText.setText(String.join(", ", movie.getActors()));
        yearEditText.setText(String.valueOf(movie.getYear()));
        lengthEditText.setText(String.valueOf(movie.getLength()));
        shortDescriptionEditText.setText(movie.getShortDescription());
        mpaRatingEditText.setText(movie.getMpaRating());
        criticsRatingEditText.setText(String.valueOf(movie.getCriticsRating()));
        posterLinkEditText.setText(movie.getPosterLink());
    }

    private void saveMovie()
    {
        String movieID = movieIDEditText.getText().toString().trim();
        String title = titleEditText.getText().toString().trim();
        String studio = studioEditText.getText().toString().trim();
        String genres = genresEditText.getText().toString().trim();
        String directors = directorsEditText.getText().toString().trim();
        String writers = writersEditText.getText().toString().trim();
        String actors = actorsEditText.getText().toString().trim();
        int year = Integer.parseInt(yearEditText.getText().toString().trim());
        int length = Integer.parseInt(lengthEditText.getText().toString().trim());
        String shortDescription = shortDescriptionEditText.getText().toString().trim();
        String mpaRating = mpaRatingEditText.getText().toString().trim();
        String posterLink = posterLinkEditText.getText().toString().trim();
        double criticsRating = Double.parseDouble(criticsRatingEditText.getText().toString().trim());
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        criticsRating = Double.parseDouble(decimalFormat.format(criticsRating));

        if (movieID.isEmpty() || title.isEmpty() || studio.isEmpty() || genres.isEmpty() || directors.isEmpty() ||
                writers.isEmpty() || actors.isEmpty() || shortDescription.isEmpty() ||
                mpaRating.isEmpty())
        {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // If movie is being edited set current id or create random UUID
        Movie requestBody = new Movie(isEditingMode ? movie.getId() : UUID.randomUUID().toString(), movieID, title,
                studio, Arrays.asList(genres.split(", ")), Arrays.asList(directors.split(", ")),
                Arrays.asList(writers.split(", ")), Arrays.asList(actors.split(", ")), year, length,
                shortDescription, mpaRating, criticsRating, posterLink);

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://9bea-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        Call<Void> call;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;

        if (isEditingMode)
        {
            call = apiService.updateMovie(authorizationHeader, movie.getId(), requestBody);
        } else
        {
            call = apiService.addMovie(authorizationHeader, requestBody);
        }

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                Log.d("ADD ", response.toString());
                if (response.isSuccessful())
                {
                    // Movie updated or created successfully
                    finish();
                } else
                {
                    // Handle the error here (e.g., show a Toast)
                    Toast.makeText(AddEditActivity.this, "Failed to update movie.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                // Handle the error here (e.g., show a Toast)
                Toast.makeText(AddEditActivity.this, "Failed to update movie. Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}