// File Name: BookAdapter.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.mdev1004.assignment4;

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

import ca.mdev1004.assignment4.R;
import ca.mdev1004.assignment4.models.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>
{
    private List<Book> books;
    private final Context context;

    public BookAdapter(List<Book> books, Context context)
    {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position)
    {
        if (books == null)
        {
            return;
        }

        Book book = books.get(position);

        holder.nameTextView.setText(book.getTitle());
        holder.studioTextView.setText(book.getStudio());
        holder.criticsRatingTextView.setText(String.valueOf(book.getCriticsRating()));

        if (book.getPosterLink() != null && !book.getPosterLink().isEmpty())
        {
            Glide
                    .with(context)
                    .load(book.getPosterLink())
                    .fallback(R.drawable.placeholder_poster)
                    .placeholder(R.drawable.placeholder_poster)
                    .fitCenter()
                    .into(holder.posterImageView);
        }

        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_BOOK_ID, book);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount()
    {
        return books.size();
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
        this.notifyDataSetChanged();
    }

    public Context getContext()
    {
        return this.context;
    }

    public void deleteItem(int position)
    {
        deleteBook(position);
    }


    private void deleteBook(int position)
    {
        Book book = books.get(position);

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://beac-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String authToken = preferences.getString("AuthToken", "");

        // Add the AuthToken to the request headers
        String authorizationHeader = "Bearer " + authToken;
        Call<Void> call = apiService.deleteBook(authorizationHeader, book.getId());

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    books.remove(position);
                    notifyItemRemoved(position);
                } else
                {
                    // Handle the error here (e.g., show a Toast)
                    Toast.makeText(context, "Failed to delete book.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                // Handle the error here (e.g., show a Toast)
                Toast.makeText(context, "Failed to delete book. Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class BookViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        TextView studioTextView;
        TextView criticsRatingTextView;
        ImageView posterImageView;

        public BookViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            studioTextView = itemView.findViewById(R.id.studioTextView);
            criticsRatingTextView = itemView.findViewById(R.id.criticsRatingTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
