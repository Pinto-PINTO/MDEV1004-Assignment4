// File Name: user.js
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024

"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const Schema = mongoose_1.default.Schema;
const passport_local_mongoose_1 = __importDefault(require("passport-local-mongoose"));
const UserSchema = new Schema({
    username: { type: String, required: true },
    emailAddress: { type: String, required: true },
    displayName: { type: String, required: true },
    created: {
        type: Date,
        default: Date.now(),
    },
    updated: {
        type: Date,
        default: Date.now(),
    },
}, {
    collection: "users",
});
UserSchema.plugin(passport_local_mongoose_1.default);
const Model = mongoose_1.default.model("User", UserSchema);
exports.default = Model;
//# sourceMappingURL=user.js.map