// File Name: book.ts
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

import { Collection, Schema, model } from "mongoose";

// Book Interface - defines the structure of a book
interface IBook {
    bookID: string;
    bookName: string;
    isbn: string;
    rating: number;
    genre: string;
    author: string;
}

// Book Schema - defines the structure of a book using the Book Interface
let bookSchema = new Schema<IBook>({
    bookID: String,
    bookName: String,
    isbn: String,
    rating: Number,
    genre: String,
    author: String,
});

let Book = model<IBook>("Book", bookSchema);

export default Book;
