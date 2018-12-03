package ar.com.instafood.models

import android.util.Log
import ar.com.instafood.activities.R
import ar.com.instafood.application.SocketApplication
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.io.Serializable

data class Check(val name: String, var products: ArrayList<Product>) : Serializable

fun getSampleCheck(application: SocketApplication): ArrayList<Check> {
    //TODO: Pasarlo a un service
    var array: ArrayList<Check> = arrayListOf()
    application.socket?.emit("getProducts");
    application.socket?.on("products", { args -> run {
        val jsonElement = JsonParser().parse((args[0] as JSONObject).toString())
        array = arrayListOf(Gson().fromJson(jsonElement, Check::class.java))

    }})
    return array
}