// File Name: book.js
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = require("mongoose");
let bookSchema = new mongoose_1.Schema({
    bookID: String,
    bookName: String,
    isbn: String,
    rating: Number,
    genre: String,
    author: String,
});
let Book = (0, mongoose_1.model)("Book", bookSchema);
exports.default = Book;
//# sourceMappingURL=book.js.map