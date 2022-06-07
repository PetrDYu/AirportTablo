package ru.petr.airporttablo

import android.app.Application
import ru.petr.airporttablo.data.models.ApiHelper
import ru.petr.airporttablo.data.models.RetrofitClient
import ru.petr.airporttablo.data.models.RetrofitServices
import ru.petr.airporttablo.data.repositories.FlightsRepository

class TabloApp : Application() {
    private val BASE_URL = "https://api.rasp.yandex.net/v3.0/"
    val retrofitServices: RetrofitServices by lazy { RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java) }
    val apiHelper: ApiHelper by lazy { ApiHelper(retrofitServices) }
    val flightsRepository by lazy { FlightsRepository(apiHelper) }
}