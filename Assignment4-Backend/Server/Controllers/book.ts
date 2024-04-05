// File Name: book.ts
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

import { Request, Response, NextFunction } from "express";

import Book from "../Models/book";

/**
 * This function sanitizes the array of strings
 *
 * @param {string | string[]} unsanitizedValue
 * @returns {string[]}
 */
function SanitizeArray(unsanitizedValue: string | string[]): string[] {
    if (Array.isArray(unsanitizedValue)) {
        return unsanitizedValue.map((value) => value.trim());
    } else if (typeof unsanitizedValue === "string") {
        return unsanitizedValue.split(",").map((value) => value.trim());
    } else {
        return [];
    }
}
/* API Functions */

/**
 * This function displays the Book List
 *
 * @export
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
export function DisplayBookList(req: Request, res: Response, next: NextFunction): void {
    // Find all Books in the Book collection
    Book.find({})
        .then(function (data) {
            res.status(200).json({ success: true, msg: "Book List Retrieved and Displayed", data: data });
        })
        .catch(function (err) {
            console.error(err);
        });
}

/**
 * This function displays a single book by the provided ID
 *
 * @export
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
export function DisplayBookByID(req: Request, res: Response, next: NextFunction): void {
    try {
        // Get the id from the url
        let id = req.params.id;

        // Find the Book by id
        Book.findById({ _id: id })
            .then(function (data) {
                res.status(200).json({ success: true, msg: "Move Retrieved by ID", data: data });
            })
            .catch(function (err) {
                console.error(err);
            });
    } catch {
        res.json({ success: false, msg: "No Data to Display" });
    }
}

/**
 * This function adds a new book to the database
 *
 * @export
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
export function AddBook(req: Request, res: Response, next: NextFunction): void {
    try {
        // Sanitize the array
        let genres = SanitizeArray(req.body.genres as string);
        let directors = SanitizeArray(req.body.directors as string);
        let writers = SanitizeArray(req.body.writers as string);
        let actors = SanitizeArray(req.body.actors as string);

        // Instantiate a new Book
        let book = new Book({
            bookID: req.body.bookID,
            bookName: req.body.bookName,
            isbn: req.body.isbn,
            rating: req.body.rating,
            author: req.body.author,
            genre: req.body.genre,
        });

        // Create a new book and pass it to the db
        Book.create(book)
            .then(function () {
                res.status(200).json({ success: true, msg: "Book Added Successfully", data: book });
            })
            .catch(function (err) {
                console.error(err);
            });
    } catch {
        res.json({ success: false, msg: "No Data to Add" });
    }
}

/**
 * This function removes a book from the database by the provided ID
 *
 * @export
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
export function UpdateBook(req: Request, res: Response, next: NextFunction): void {
    try {
        // Get the id from the url
        let id = req.params.id;


        // Instantiate a new Book Object
        let bookToUpdate = new Book({
            _id: id,
            bookID: req.body.bookID,
            bookName: req.body.bookName,
            isbn: req.body.isbn,
            rating: req.body.rating,
            author: req.body.author,
            genre: req.body.genre,
        });

        // Find the Book by id and then update
        Book.updateOne({ _id: id }, bookToUpdate)
            .then(function () {
                res.status(200).json({ success: true, msg: "Book Updated Successfully", data: bookToUpdate });
            })
            .catch(function (err) {
                console.error(err);
            });
    } catch {
        res.json({ success: false, msg: "No Data to Update" });
    }
}

/**
 * This function removes a book from the database by the provided ID
 *
 * @export
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
export function DeleteBook(req: Request, res: Response, next: NextFunction): void {
    try {
        // Get the id from the url
        let id = req.params.id;

        // Find the Book by id and then delete
        Book.deleteOne({ _id: id })
            .then(function () {
                res.json(id);
            })
            .catch(function (err) {
                console.error(err);
            });
    } catch {
        res.json({ success: false, msg: "No Data to Delete" });
    }
}
