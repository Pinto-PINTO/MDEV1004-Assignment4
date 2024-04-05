package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

import com.squareup.moshi.Json;

import java.util.List;

public class BookListResponse extends BasicResponse
{
    @Json(name = "data") private List<Book> books;

    public List<Book> getBooks()
    {
        return books;
    }
}
