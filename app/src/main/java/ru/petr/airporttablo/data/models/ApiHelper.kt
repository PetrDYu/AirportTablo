package ru.petr.airporttablo.data.models

class ApiHelper (private val apiServices: RetrofitServices) {
    suspend fun getArrivalFlights(date: String, limit: Int, offset: Int) = apiServices.getArrivalFlights(date, limit, offset)
    suspend fun getDepartureFlights(date: String, limit: Int, offset: Int) = apiServices.getDepartureFlights(date, limit, offset)
}