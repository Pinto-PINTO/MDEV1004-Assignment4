// File Name: book.js
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DeleteBook = exports.UpdateBook = exports.AddBook = exports.DisplayBookByID = exports.DisplayBookList = void 0;
const book_1 = __importDefault(require("../Models/book"));
function SanitizeArray(unsanitizedValue) {
    if (Array.isArray(unsanitizedValue)) {
        return unsanitizedValue.map((value) => value.trim());
    }
    else if (typeof unsanitizedValue === "string") {
        return unsanitizedValue.split(",").map((value) => value.trim());
    }
    else {
        return [];
    }
}
function DisplayBookList(req, res, next) {
    book_1.default.find({})
        .then(function (data) {
        res.status(200).json({ success: true, msg: "Book List Retrieved and Displayed", data: data });
    })
        .catch(function (err) {
        console.error(err);
    });
}
exports.DisplayBookList = DisplayBookList;
function DisplayBookByID(req, res, next) {
    try {
        let id = req.params.id;
        book_1.default.findById({ _id: id })
            .then(function (data) {
            res.status(200).json({ success: true, msg: "Move Retrieved by ID", data: data });
        })
            .catch(function (err) {
            console.error(err);
        });
    }
    catch {
        res.json({ success: false, msg: "No Data to Display" });
    }
}
exports.DisplayBookByID = DisplayBookByID;
function AddBook(req, res, next) {
    try {
        let book = new book_1.default({
            bookID: req.body.bookID,
            bookName: req.body.bookName,
            isbn: req.body.isbn,
            rating: req.body.rating,
            author: req.body.author,
            genre: req.body.genre,
        });
        book_1.default.create(book)
            .then(function () {
            res.status(200).json({ success: true, msg: "Book Added Successfully", data: book });
        })
            .catch(function (err) {
            console.error(err);
        });
    }
    catch {
        res.json({ success: false, msg: "No Data to Add" });
    }
}
exports.AddBook = AddBook;
function UpdateBook(req, res, next) {
    try {
        let id = req.params.id;
        let bookToUpdate = new book_1.default({
            _id: id,
            bookID: req.body.bookID,
            bookName: req.body.bookName,
            isbn: req.body.isbn,
            rating: req.body.rating,
            author: req.body.author,
            genre: req.body.genre,
        });
        book_1.default.updateOne({ _id: id }, bookToUpdate)
            .then(function () {
            res.status(200).json({ success: true, msg: "Book Updated Successfully", data: bookToUpdate });
        })
            .catch(function (err) {
            console.error(err);
        });
    }
    catch {
        res.json({ success: false, msg: "No Data to Update" });
    }
}
exports.UpdateBook = UpdateBook;
function DeleteBook(req, res, next) {
    try {
        let id = req.params.id;
        book_1.default.deleteOne({ _id: id })
            .then(function () {
            res.json(id);
        })
            .catch(function (err) {
            console.error(err);
        });
    }
    catch {
        res.json({ success: false, msg: "No Data to Delete" });
    }
}
exports.DeleteBook = DeleteBook;
//# sourceMappingURL=book.js.map