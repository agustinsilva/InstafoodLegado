package ar.com.instafood.interfaces

import ar.com.instafood.models.RestaurantsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface RestaurantsService {

    @GET("restaurants")
    fun getRestaurants(): Observable<RestaurantsResponse>

    companion object {
        fun create(): RestaurantsService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://instafood-server.herokuapp.com/instafood-api/")
                    .build()

            return retrofit.create(RestaurantsService::class.java)
        }
    }
}