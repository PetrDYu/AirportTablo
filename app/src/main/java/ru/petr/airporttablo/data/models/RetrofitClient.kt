package ru.petr.airporttablo.data.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "f2298149-f747-4569-bf39-fe9b4b61cb09"


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

interface RetrofitServices {
    @GET("schedule?apikey=$API_KEY&station=SVO&transport_types=plane&event=arrival&system=iata")
    suspend fun getArrivalFlights(
        @Query("date") date: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): FlightsInfo

    @GET("schedule?apikey=$API_KEY&station=SVO&transport_types=plane&event=departure&system=iata")
    suspend fun getDepartureFlights(
        @Query("date") date: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): FlightsInfo
}