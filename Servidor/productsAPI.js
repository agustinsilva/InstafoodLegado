const express = require('express');
const bodyParser = require('body-parser');
let fileSystem = require('fs');
let path = require('path');
const productsAPI = express();
productsAPI.use(bodyParser.json()); // for parsing application/json

productsAPI.get('/instafood-api/products', function (req, res) {
    try {
        let json = json = { products: [{ name: "Hamburguesa" }, { name: "Papas" }, { name: "Fernet" }] };
        res.send(json);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/restaurants/:id/menues', function (req, res) {
    let id = req.params.id;
    try {
        let json = {
            menues: [{
                "mainProducts":
                    [{
                        "title": "Burger Facts",
                        "description": "Triple Carne con Provoleta, Panceta, Cebolla Crispy, Queso Dambo y salsa de mayochurri", 
                        "price": 240, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/0/image"
                    },
                    { "title": "Burger Love", "description": "Doble carne, cheddar, dambo, cebolla morada, pepinos, panceta, cebolla crocante y mucho alioli",
                     "price": 220, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/1/image" }, 
                    {
                        "title": "Premium", "description": "Queso cheddar, panceta, cebollas caramelizadas y tomate", "price": 170,
                        "resource": "http://instafood-server.herokuapp.com/instafood-api/products/2/image"
                    },
                    { "title": "HeartBreaker", "description": "200 gramos de carne, pulled pork, queso cheddar, tomate, pepino, cebolla crocante y BBQ", "price": 200, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/3/image" }, 
                    { "title": "Donkey Donuts", "description": "200 gramos de carne con donas glaseadas, panceta y salsa de mostaza", 
                    "price": 30, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/4/image" }],
                     "drinkProducts": [{ "title": "Birra Artesanal", "description": "De la mejor", "price": 220, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/5/image" }, 
                     { "title": "Coca Cola", "description": "mucho azucar", "price": 50, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/6/image" }, 
                     { "title": "Sprite", "description": "Alimona", "price": 220, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/7/image" }, 
                     { "title": "Fernet", "description": "Fernet Branca", "price": 220, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/8/image" },
                      { "title": "Birra", "description": "Heineken", "price": 220, 
                      "resource": "http://instafood-server.herokuapp.com/instafood-api/products/9/image" }], 
                      "secondaryProducts": [
                          { "title": "Papas con cheddar", "description": "papas - panceta - cheddar", 
                          "price": 140, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/10/image" },
                           { "title": "Salchichitas", "description": "salchis", "price": 150, 
                           "resource": "http://instafood-server.herokuapp.com/instafood-api/products/11/image" }, 
                           { "title": "Tabla de jamones", "description": "Jamoncito", "price": 200,
                            "resource": "http://instafood-server.herokuapp.com/instafood-api/products/12/image" }, 
                            { "title": "Bastones de muzzarella", "description": "Bastoncitos de queso", "price": 120,
                             "resource": "http://instafood-server.herokuapp.com/instafood-api/products/13/image" }]
            },
            {
                "mainProducts":
                    [{
                        "title": "La bestia",
                        "description": "Contanos si después de esto...llegas a comer las papas", 
                        "price": 210, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/14/image"
                    },
                    { "title": "La mantecosa", 
                    "description": "Cebolla crispy + cebolla caramelizada + queso tybo + queso cheddar y manteca ",
                     "price": 180, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/15/image" }],
                     "drinkProducts": [{ "title": "Birra Artesanal", "description": "De la mejor", "price": 220, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/5/image" }, 
                     { "title": "Coca Cola", "description": "mucho azucar", "price": 50, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/6/image" }], 
                     "secondaryProducts": [{ "title": "Papas con cheddar", "description": "papas - panceta - cheddar", 
                     "price": 140, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/10/image" }]
            },
            {
                "mainProducts":
                    [{
                        "title": "La demente",
                        "description": "Te vuela la cabeza desde el primer bocado", 
                        "price": 195, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/16/image"
                    },
                    { "title": "La porky", "description": "Panceta y chancho por todos lados!",
                     "price": 160, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/17/image" }],
                     "drinkProducts": [{ "title": "Birra Artesanal", "description": "De la mejor", "price": 220, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/5/image" }, 
                     { "title": "Coca Cola", "description": "mucho azucar", "price": 50, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/6/image" }], 
                     "secondaryProducts": [{ "title": "Papas con cheddar", "description": "papas - panceta - cheddar", "price": 140, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/10/image" },
                      { "title": "Salchichitas", "description": "salchis", "price": 150,
                       "resource": "http://instafood-server.herokuapp.com/instafood-api/products/11/image" }]
            },
            {
                "mainProducts":
                    [{
                        "title": "Cajita Feliz",
                        "description": "Papas kids y gaseosa", 
                        "price": 155, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/18/image"
                    },
                    { "title": "Doble Cuarto de libra", 
                    "description": "Hamburguesa de carne 100% vacuna con doble queso cheddar, cebolla fresca, mostaza y kétchup",
                     "price": 285, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/19/image" }],
                     "drinkProducts": [
                     { "title": "Coca Cola", "description": "mucho azucar", "price": 50, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/6/image" }], 
                     "secondaryProducts": [{ "title": "Sticks de pollo", 
                     "description": "Patitas de pollo (5 unidades)", "price": 99, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/20/image" }]
            },
            {
                "mainProducts":
                    [{
                        "title": "Combo Whopper",
                        "description": "Carne Whopper a la parrilla, tomate, lechiga, cebolla, pepinos, ketchup y mayonesa. Acompañado con una bebida y una papas fritas regulares.", 
                        "price": 260, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/21/image"
                    },
                    { "title": "Combo BK Stacker Cuádruple", "description": "Cuatros carnes a la parrilla, queso cheddar, panceta y salsa especial. Acompañado con una bebida y una papas fritas regulares.",
                     "price": 302, "resource": "http://instafood-server.herokuapp.com/instafood-api/products/22/image" }],
                     "drinkProducts": [
                     { "title": "Coca Cola", "description": "mucho azucar", "price": 50, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/6/image" }], 
                     "secondaryProducts": [{ "title": "Papas con cheddar", "description": "papas - panceta - cheddar", "price": 140, 
                     "resource": "http://instafood-server.herokuapp.com/instafood-api/products/10/image" },
                      { "title": "Combo BK snacks con empanadas de carne", "description": "Bandeja con los mejores acompañamientos: Nuggets de pollo, papas fritas y empanaditas de carne. Acompañado con una bebida y unas papas fritas regulares.", "price": 200,
                       "resource": "http://instafood-server.herokuapp.com/instafood-api/products/23/image" }]
            }
            ]};
        let menu = json.menues[id];
        res.send(menu);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/restaurants', function (req, res) {
    try {
        let json = {
            restaurants: [
                {
                    id: "0", title: "La Birra Bar", description: "Carlos Calvo 4317", distance: "1",
                    longitud: -58.4260301, latitud: -34.626635, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/image",
                    schedule: "8:00 a 12:00 hs", background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/background_image"
                },
                {
                    id: "1", title: "Aloha", description: "Av. Chiclana 3299", distance: "2",
                    longitud: -58.4110185, latitud: -34.634183, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/image",
                    schedule: "10:00 a 20:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/background_image"
                },
                {
                    id: "2", title: "Perez-H", description: "Defensa 435", distance: "10",
                    longitud: -58.3719893, latitud: -34.6128418, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/image",
                    schedule: "7:00 a 19:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/background_image"
                },
                {
                    id: "3", title: "McDonalds", description: "Av. Belgrano 985", distance: "12",
                    longitud: -58.3801741, latitud: -34.612804, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/image",
                    schedule: "12:00 a 15:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/background_image"
                },
                {
                    id: "4", title: "Burger King", description: "Av. Corrientes 206", distance: "13",
                    longitud: -58.37046390, latitud: -34.6032068, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/image",
                    schedule: "9:00 a 18:00 hs", background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/background_image"
                }
            ]
        };
        res.send(json);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/restaurants/:id', function (req, res) {
    try {
        let id = req.params.id;
        let restaurantList = {
            restaurants: [
                {
                    id: "0", title: "La Birra Bar", description: "Carlos Calvo 4317", distance: "1",
                    longitud: -58.4260301, latitud: -34.626635, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/image",
                    schedule: "8:00 a 12:00 hs", background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/background_image"
                },
                {
                    id: "1", title: "Aloha", description: "Av. Chiclana 3299", distance: "2",
                    longitud: -58.4110185, latitud: -34.634183, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/image",
                    schedule: "10:00 a 20:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/background_image"
                },
                {
                    id: "2", title: "Perez-H", description: "Defensa 435", distance: "10",
                    longitud: -58.3719893, latitud: -34.6128418, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/image",
                    schedule: "7:00 a 19:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/background_image"
                },
                {
                    id: "3", title: "McDonalds", description: "Av. Belgrano 985", distance: "12",
                    longitud: -58.3801741, latitud: -34.612804, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/image",
                    schedule: "12:00 a 15:00 hs",  background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/background_image"
                },
                {
                    id: "4", title: "Burger King", description: "Av. Corrientes 206", distance: "13",
                    longitud: -58.37046390, latitud: -34.6032068, resource: "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/image",
                    schedule: "9:00 a 18:00 hs", background_url: "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/background_image"
                }
            ]
        };
        let restaurant = restaurantList.restaurants[id];
        res.send(restaurant);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/restaurants/:id/background_image', function (req, res) {
    try {
        let id = req.params.id;
        let images_path = ["./background_images/birrabackground.jpg","./background_images/aloha_background.jpg",
        "./background_images/perez_h_background.jpg","./background_images/mcdonalds_background.jpg",
        "./background_images/burger_king_background.jpg"];
        let relative_path = images_path[id];
        var filePath = path.join(__dirname, relative_path);
        let data = fileSystem.readFileSync(filePath);
        res.contentType("image/jpg");
        res.send(data);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/products/:id/image', function (req, res) {
    try {
        let id = req.params.id;
        let images_path = ["./images/burgerfacts.jpg", "./images/burgerlove.jpg",
            "./images/premium.jpg", "./images/heartbreaker.jpg", "./images/donut.jpg", "./images/cerveza_artesanal.jpg",
            "./images/cocacola.jpg", "./images/sprite.jpg", "./images/Fernetbranca.jpg", "./images/heineken.jpg",
            "./images/papas_cheddar_panceta.jpg", "./images/salchichitas.jpg", "./images/tabla_jamones.jpg",
            "./images/bastoncitos_mozzarella.jpg","./images/la_bestia.jpg","./images/mantecosa.jpg","./images/demente.jpg",
        "./images/porky.jpg","./images/cajita_feliz.jpg","./images/doble_cuarto.jpg","./images/stick_pollo.jpg",
        "./images/combo_whopper.jpg","./images/stacker_cuadruple.jpg","./images/snacks_empanadas.jpg"
    ];
        let relative_path = images_path[id];
        var filePath = path.join(__dirname, relative_path);
        let data = fileSystem.readFileSync(filePath);
        res.contentType("image/jpg");
        res.send(data);
    } catch (err) {
        res.status(500).send(err);
    }
})

productsAPI.get('/instafood-api/restaurants/:id/image', function (req, res) {
    try {
        let id = req.params.id;
        let images_path = ["./images/logo_birrabar.jpg", "./images/burgermood.jpg",
            "./images/perezh.jpg", "./images/mcdonalds.jpg", "./images/burgerking.jpg"];
        let relative_path = images_path[id];
        var filePath = path.join(__dirname, relative_path);
        let data = fileSystem.readFileSync(filePath);
        res.contentType("image/png");
        res.send(data);
    } catch (err) {
        res.status(500).send(err);
    }
})
module.exports = productsAPI