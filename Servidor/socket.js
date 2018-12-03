function connectedSocket(socket, io, variable) {
    console.log("socketConectado", socket.id);
    socket.on("connectedSocket", (name) => {
        if (variable.some(v => v.name === name)) {
            const sock = variable.find(v => v.name === name)
            sock.sockets.push(socket.id)
            sock.sockets = [ ...new Set(sock.sockets)]
        } else {
            variable.push({name: name, sockets: [socket.id]})
        }
        io.emit("connectedResponse", variable)
        console.log('variable', variable)
    })
}

function productSelected(socket, io,  variable) {
    socket.on("productSelected", (product) => {
        let aux = variable.find(v => v.sockets.includes(socket.id))
        if (aux.product && aux.product.length) {
            searchUser(product, aux)
        } else {
            aux.product = [];
            aux.product.push(JSON.parse(product))  
        }
        aux.sockets.forEach(socket => io.to(socket).emit("products", aux.product))
        console.log(JSON.stringify(aux))
    })
}

function searchUser(product, aux) {
    const name = JSON.parse(product).name;
    const userFound = aux.product.find(p => p.name === name);
    if (userFound) {
        userFound.products && userFound.products.length ? userFound.products = userFound.products.concat(JSON.parse(product).products) : userFound.products = JSON.parse(product).products
    } else {
        aux.product.push(JSON.parse(product));
    } 
}

function getProducts(socket, io, variable) {
    socket.on("getProducts", () => {
        let aux = variable.find(v => v.sockets.includes(socket.id))
        aux.sockets.forEach(socket => io.to(socket).emit("products", aux.product))
        console.log(aux)
    })
}

function addNewUser(socket, io, variable) {
    socket.on("addNewUser", (user) => {
        console.log(user)
        let aux = variable.find(v => v.name === user);
        aux.sockets.push(socket.id);
    })
}

module.exports = { connectedSocket, productSelected, getProducts, addNewUser }