package ar.com.instafood.models

import ar.com.instafood.activities.R
import java.io.Serializable

data class Product(val title : String, val description : String, val price : Int, val resource : String) : Serializable {
    var listProduct : ArrayList<Product> = arrayListOf<Product>()


    fun addNewOne(product: Product) {
        listProduct.add(product)
    }

    fun getProducList(): ArrayList<Product> {
        return listProduct
    }


}

fun getSampleProducts(): ArrayList<Product> {
    return arrayListOf(
            Product("180 gr de burger", "180 gr burger - panceta - huevo", 220, "http://instafood-server.herokuapp.com/instafood-api/products/0/image"),
            Product("Burger mood", "Happy Burger con salsa picante", 500, "http://instafood-server.herokuapp.com/instafood-api/products/0/image"),
            Product("Perez-H", "Burger raton perez con palmito", 100, "http://instafood-server.herokuapp.com/instafood-api/products/0/image"),
            Product("McDonalds", "Burger Industrial con \"carne\"", 120, "http://instafood-server.herokuapp.com/instafood-api/products/0/image"),
            Product("Burger King", "Carne del rey con mayonesa", 30, "http://instafood-server.herokuapp.com/instafood-api/products/0/image")
    )
}


fun getMainProducts(): ArrayList<Product> {
    return arrayListOf(
            Product("Burger Facts", "Triple Carne con Provoleta, Panceta, Cebolla Crispy, Queso Dambo y salsa de mayochurri", 240, "http://instafood-server.herokuapp.com/instafood-api/products/0/image"),
            Product("Burger Love", "Doble carne, cheddar, dambo, cebolla morada, pepinos, panceta, cebolla crocrante y mucho alioli", 220, "http://instafood-server.herokuapp.com/instafood-api/products/1/image"),
            Product("Premium", "Queso cheddar, pancete, cebollas caramelizadas y tomate", 170, "http://instafood-server.herokuapp.com/instafood-api/products/2/image"),
            Product("HeartBreaker", "200 gr de carne, pulled pork, queso cheddar, tomate, pepino, cebolla crocante y BBQ", 200, "http://instafood-server.herokuapp.com/instafood-api/products/3/image"),
            Product("Donkey Donuts", "200 gr de carne con donas glaseadas, panceta y salsa de mostaza", 30, "http://instafood-server.herokuapp.com/instafood-api/products/4/image")
    )
}

fun getDrinkProducts(): ArrayList<Product> {
    return arrayListOf(
            Product("Birra Artesanal","de la mejor",220,"http://instafood-server.herokuapp.com/instafood-api/products/5/image"),
            Product("Coca Cola","mucho azucar",50, "http://instafood-server.herokuapp.com/instafood-api/products/6/image"),
            Product("Sprite","alimonada",220, "http://instafood-server.herokuapp.com/instafood-api/products/7/image"),
            Product("Fernet","Fernet BRanca",220, "http://instafood-server.herokuapp.com/instafood-api/products/8/image"),
            Product("Birra","Heineken",220, "http://instafood-server.herokuapp.com/instafood-api/products/9/image")
    )
}

fun getSecondaryProducts(): ArrayList<Product> {
    return arrayListOf(
            Product("Papas con chedart","papas - panceta - chedart",140, "http://instafood-server.herokuapp.com/instafood-api/products/10/image"),
            Product("salchichitas","salchis",150, "http://instafood-server.herokuapp.com/instafood-api/products/11/image"),
            Product("tabla de jamones","jamoncito",200, "http://instafood-server.herokuapp.com/instafood-api/products/12/image"),
            Product("bastones de muzzarella","bastoncitos de queso",120, "http://instafood-server.herokuapp.com/instafood-api/products/13/image")
    )
}