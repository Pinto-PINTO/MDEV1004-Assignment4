// File Name: index.ts
// Student Name: Chesta Patel
// Student ID: 200542446
// Date: 17th August 2023

import express from "express";
let router = express.Router();
import passport from "passport";

/* Get the book controller functions */
import { DisplayBookList, DisplayBookByID, AddBook, UpdateBook, DeleteBook } from "../Controllers/book";

/* Get the auth controller functions */
import { ProcessLogin, ProcessLogout, ProcessRegisterPage } from "../Controllers/user";

/* GET /api/list - display the book list */
router.get("/list", passport.authenticate("jwt", { session: false }), (req, res, next) =>
    DisplayBookList(req, res, next)
);

/* GET /api/find/:id - display a book by id */
router.get("/find/:id", passport.authenticate("jwt", { session: false }), (req, res, next) =>
    DisplayBookByID(req, res, next)
);

/* POST /api/add - add a new book */
router.post("/add", passport.authenticate("jwt", { session: false }), (req, res, next) => AddBook(req, res, next));

/* PUT /api/update/:id - update a book by id */
router.put("/update/:id", passport.authenticate("jwt", { session: false }), (req, res, next) =>
    UpdateBook(req, res, next)
);

/* GET /api/delete/:id - delete a book by id */
router.delete("/delete/:id", passport.authenticate("jwt", { session: false }), (req, res, next) =>
    DeleteBook(req, res, next)
);

/* POST /api/register - add a new user */
router.post("/register", (req, res, next) => ProcessRegisterPage(req, res, next));

/* POST /api/login - login a user */
router.post("/login", (req, res, next) => ProcessLogin(req, res, next));

/* GET /api/logout - logout a user */
router.get("/logout", (req, res, next) => ProcessLogout(req, res, next));

export default router;
