package ca.mdev1004.assignment4.models;

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
