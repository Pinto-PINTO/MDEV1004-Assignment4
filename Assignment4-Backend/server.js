#!/usr/bin/env node

// File Name: server.js
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024


"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const app_1 = __importDefault(require("./Server/Config/app"));
const debug_1 = __importDefault(require("debug"));
(0, debug_1.default)('apitsc:server');
const http_1 = __importDefault(require("http"));
let port = normalizePort(process.env.PORT || '3000');
app_1.default.set('port', port);
let server = http_1.default.createServer(app_1.default);
server.listen(port);
server.on('error', onError);
server.on('listening', onListening);
function normalizePort(val) {
    let port = parseInt(val, 10);
    if (isNaN(port)) {
        return val;
    }
    if (port >= 0) {
        return port;
    }
    return false;
}
function onError(error) {
    if (error.syscall !== 'listen') {
        throw error;
    }
    let bind = typeof port === 'string'
        ? 'Pipe ' + port
        : 'Port ' + port;
    switch (error.code) {
        case 'EACCES':
            console.error(bind + ' requires elevated privileges');
            process.exit(1);
            break;
        case 'EADDRINUSE':
            console.error(bind + ' is already in use');
            process.exit(1);
            break;
        default:
            throw error;
    }
}
function onListening() {
    let addr = server.address();
    let bind = typeof addr === 'string'
        ? 'pipe ' + addr
        : 'port ' + addr.port;
    (0, debug_1.default)('Listening on ' + bind);
}
//# sourceMappingURL=server.js.map