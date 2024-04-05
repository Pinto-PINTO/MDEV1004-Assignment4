// File Name: MovieAdapter.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private List<Movie> movies;
    private final Context context;

    public MovieAdapter(List<Movie> movies, Context context)
    {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        if (movies == null)
        {
            return;
        }

        Movie movie = movies.get(position);

        holder.nameTextView.setText(movie.getTitle());
        holder.studioTextView.setText(movie.getStudio());
        holder.criticsRatingTextView.setText(String.valueOf(movie.getCriticsRating()));

        if (movie.getPosterLink() != null && !movie.getPosterLink().isEmpty())
        {
            Glide
                    .with(context)
                    .load(movie.getPosterLink())
                    .fallback(R.drawable.placeholder_poster)
                    .placeholder(R.drawable.placeholder_poster)
                    .fitCenter()
                    .into(holder.posterImageView);
        }

        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_MOVIE_ID, movie);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public void setMovies(List<Movie> movies)
    {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    public Context getContext()
    {
        return this.context;
    }

    public void deleteItem(int position)
    {
        deleteMovie(position);
    }


    private void deleteMovie(int position)
    {
        Movie movie = movies.get(position);

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://beac-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;
        Call<Void> call = apiService.deleteMovie(authorizationHeader, movie.getId());

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    movies.remove(position);
                    notifyItemRemoved(position);
                } else
                {
                    // Handle the error here (e.g., show a Toast)
                    Toast.makeText(context, "Failed to delete movie.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                // Handle the error here (e.g., show a Toast)
                Toast.makeText(context, "Failed to delete movie. Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        TextView studioTextView;
        TextView criticsRatingTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            studioTextView = itemView.findViewById(R.id.studioTextView);
            criticsRatingTextView = itemView.findViewById(R.id.criticsRatingTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
