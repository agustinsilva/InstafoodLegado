package ar.com.instafood.application

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket

import java.net.URISyntaxException

class SocketApplication : Application() {

    var socket: Socket? = null
        private set

    init {
        try {
            socket = IO.socket("https://instafood-server.herokuapp.com/")
            socket?.connect()
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }
}