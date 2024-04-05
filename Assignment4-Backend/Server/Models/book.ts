// File Name: book.ts
// Student Name: Chesta Patel
// Student ID: 200542446
// Date: 17th August 2023

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
