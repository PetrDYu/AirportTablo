package ru.petr.airporttablo.data.repositories

import ru.petr.airporttablo.data.models.ApiHelper
import ru.petr.airporttablo.data.models.Flight
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "yyyy-MM-dd"

class FlightsRepository (private val apiHelper: ApiHelper) {

    suspend fun getArrivalFlights(date: Date, limit: Int, offset: Int): List<Flight> {
        return apiHelper.getArrivalFlights(
            SimpleDateFormat(DATE_FORMAT).format(date),
            limit,
            offset
        ).flights
    }

    suspend fun getDepartureFlights(date: Date, limit: Int, offset: Int): List<Flight> {
        return apiHelper.getDepartureFlights(
            SimpleDateFormat(DATE_FORMAT).format(date),
            limit,
            offset
        ).flights
    }
}