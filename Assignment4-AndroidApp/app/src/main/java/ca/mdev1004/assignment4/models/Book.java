package ca.mdev1004.assignment4.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable
{
    @Json(name = "_id")
    private String id;
    @Json(name = "bookID")
    private String bookID;
    @Json(name = "bookName")
    private String bookName;
    @Json(name = "isbn")
    private String isbn;
    @Json(name = "rating")
    private int rating;
    @Json(name = "genre")
    private String genre;
    @Json(name = "author")
    private String author;
    @Json(name = "posterlink")
    private String posterLink;

    public Book(String id, String bookID, String bookName, String isbn, int rating, String genre, String author, String posterLink)
    {
        this.id = id;
        this.bookID = bookID;
        this.bookName = bookName;
        this.isbn = isbn;
        this.rating = rating;
        this.genre = genre;
        this.author = author;
        this.posterLink = posterLink;
    }

    public String getId()
    {
        return id;
    }

    public String getBookID()
    {
        return bookID;
    }

    public String getBookName()
    {
        return bookName;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public int getRating()
    {
        return rating;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPosterLink()
    {
        return posterLink;
    }
}

