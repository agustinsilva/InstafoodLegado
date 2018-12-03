package ar.com.instafood.models

import com.google.gson.Gson
data class QrUserRestaurant (var username : String, var restaurant : Restaurant) {

    companion object {
        fun serialize(self: QrUserRestaurant): String {
            return Gson().toJson(self)
        }

        fun deserialize(jsonString: String): QrUserRestaurant {
            return Gson().fromJson(jsonString, QrUserRestaurant::class.java)
        }
    }
}