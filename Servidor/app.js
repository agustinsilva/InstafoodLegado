const express = require('express');
const app = express();
const server = require('http').createServer(app);
server.listen(process.env.PORT || 5000);
const io = require('socket.io')(server);
const productsAPI = require("./productsAPI");
const connectedSocket = require("./socket");

let variable = []

io.on('connection',  (socket) => {
    connectedSocket.connectedSocket(socket, io, variable)
    connectedSocket.productSelected(socket,io,  variable)
    connectedSocket.getProducts(socket, io, variable)
    connectedSocket.addNewUser(socket, io, variable)
})
app.use(productsAPI);
module.exports = app;