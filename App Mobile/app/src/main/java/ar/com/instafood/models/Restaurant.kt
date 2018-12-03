package ar.com.instafood.models

import android.location.Location
import ar.com.instafood.activities.R
import java.io.Serializable

data class Restaurant(var id : String,var title: String, var description: String,var distanceDouble : Double, var distance: String, var longitud: Double, var latitud: Double, var resource: String, var schedule: String, var background_url : String) : Serializable

fun setDistances(restaurants: List<Restaurant>?, currentLocation: Location?) {
    return restaurants!!.forEach { it -> calculateLoc(currentLocation, it) }
}

fun getSampleRestaurants(): List<Restaurant> {
    return listOf(
            Restaurant("0","La Birra Bar", "Carlos Calvo 4317",1.00, "1 Km.", -58.3740076  , -34.6044714, "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/image","8:00 a 12:00 hs","http://instafood-server.herokuapp.com/instafood-api/restaurants/0/image"),
            Restaurant("1","Aloha", " Av. Chiclana 3299",2.00, "2 Km.", -58.3740077, -34.6044643, "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/image","10:00 a 20:00 hs","http://instafood-server.herokuapp.com/instafood-api/restaurants/1/image"),
            Restaurant("2","Perez-H", "Defensa 435",10.00, "10 Km.", -58.3740098, -34.6044669, "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/image","7:00 a 19:00 hs","http://instafood-server.herokuapp.com/instafood-api/restaurants/2/image"),
            Restaurant("3","Mc Donalds", "Av. Belgrano 985",12.00, "12 Km.", -58.3740079, -34.6044692, "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/image","12:00 a 15:00 hs","http://instafood-server.herokuapp.com/instafood-api/restaurants/3/image"),
            Restaurant("4","Burger King", "Av. Corrientes 206",13.00, "13 Km.", -58.3740082, -34.6044711, "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/image","9:00 a 18:00 hs","http://instafood-server.herokuapp.com/instafood-api/restaurants/4/image")
    )
}

fun calculateLoc(currentLocation: Location?, restaurant: Restaurant): Restaurant {
    var location2 = Location("")
    location2.latitude = restaurant.latitud
    location2.longitude = restaurant.longitud
    restaurant.distanceDouble = currentLocation?.distanceTo(location2)!!.div(1000).toDouble()
    restaurant.distance = String.format("%.2f",restaurant.distanceDouble).replace(",",".") + " Kms"
    return restaurant
}

