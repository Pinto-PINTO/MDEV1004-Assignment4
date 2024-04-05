// File Name: index.js
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
let router = express_1.default.Router();
const passport_1 = __importDefault(require("passport"));
const book_1 = require("../Controllers/book");
const user_1 = require("../Controllers/user");
router.get("/list", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, book_1.DisplayBookList)(req, res, next));
router.get("/find/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, book_1.DisplayBookByID)(req, res, next));
router.post("/add", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, book_1.AddBook)(req, res, next));
router.put("/update/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, book_1.UpdateBook)(req, res, next));
router.delete("/delete/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, book_1.DeleteBook)(req, res, next));
router.post("/register", (req, res, next) => (0, user_1.ProcessRegisterPage)(req, res, next));
router.post("/login", (req, res, next) => (0, user_1.ProcessLogin)(req, res, next));
router.get("/logout", (req, res, next) => (0, user_1.ProcessLogout)(req, res, next));
exports.default = router;
//# sourceMappingURL=index.js.map