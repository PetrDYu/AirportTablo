package ru.petr.airporttablo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.petr.airporttablo.data.models.ApiHelper
import ru.petr.airporttablo.data.models.Flight
import ru.petr.airporttablo.data.models.utils.Resource
import ru.petr.airporttablo.data.repositories.FlightsRepository
import java.util.*

class MainViewModel(private val repository: FlightsRepository) : ViewModel() {

    /*fun getArrivalFlight(onSetState: (resource: Resource<List<Flight>>) -> Unit) = viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
        onSetState(Resource.loading(data = null))
        try {
            onSetState(Resource.success(data = repository.getArrivalFlights(Date(), 100, 0)))
            Log.d("ViewModel", "ViewModel")
        } catch (exception: Exception) {
            onSetState(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/
    fun getArrivalFlight(onSetState: (resource: Resource<List<Flight>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                onSetState(Resource.loading(data = null))
            }
            try {
                onSetState(Resource.success(data = repository.getArrivalFlights(Date(), 500, 0)))
                Log.d("ViewModel", "ViewModel")
            } catch (exception: Exception) {
                onSetState(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    fun getDepartureFlight(onSetState: (resource: Resource<List<Flight>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                onSetState(Resource.loading(data = null))
            }
            try {
                onSetState(Resource.success(data = repository.getDepartureFlights(Date(), 500, 0)))
            } catch (exception: Exception) {
                onSetState(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }
}

class MainViewModelFactory(private val repository: FlightsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}