@file:OptIn(ExperimentalPagerApi::class)

package ru.petr.airporttablo

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import ru.petr.airporttablo.data.models.Flight
import ru.petr.airporttablo.data.models.FlightsInfo
import ru.petr.airporttablo.data.models.Thread
import ru.petr.airporttablo.data.models.utils.Resource
import ru.petr.airporttablo.data.models.utils.Status
import ru.petr.airporttablo.ui.theme.AirportTabloTheme
import java.text.SimpleDateFormat
import java.util.*

const val LOG_TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels { MainViewModelFactory((application as TabloApp).flightsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AirportTabloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var resourceArrival by remember {
                        mutableStateOf(Resource.loading<List<Flight>>(null))
                    }
                    var resourceDeparture by remember {
                        mutableStateOf(Resource.loading<List<Flight>>(null))
                    }
                    Main(resourceArrival, resourceDeparture)
                    remember { viewModel.getArrivalFlight { resourceArrival = it } }
                    remember { viewModel.getDepartureFlight { resourceDeparture = it } }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(resourceArrival: Resource<List<Flight>>?, resourceDeparture: Resource<List<Flight>>?) {
    val pagerState = rememberPagerState()
    Scaffold (
        topBar = { TopBar() },
        bottomBar = { BottomPagerBar(pagerState = pagerState) }
            ) {
        HorizontalPager(count = 2, state = pagerState) { page: Int ->
            when (page) {
                0 -> FligtsPage(resource = resourceArrival)
                1 -> FligtsPage(resource = resourceDeparture)
            }
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Аэропорт Шереметьево") }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Composable
fun BottomPagerBar(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        listOf(R.string.in_button_name, R.string.out_button_name).forEachIndexed { index, id ->
            Tab(
                text = { Text(stringResource(id = id)) },
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
            )
        }
    }
}

@Composable
fun FligtsPage(resource: Resource<List<Flight>>?){
    if (resource == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    when (resource.status) {
        Status.LOADING -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        Status.SUCCESS -> {
            if (resource.data.isNullOrEmpty()) {
                Log.d(LOG_TAG, resource.data.toString())
                Text("Не найдено ни одного рейса")
            } else {
                Column() {
                    Header(Modifier.padding(horizontal = 0.dp, vertical = 0.dp))
                    FlightsList(flights = resource.data)
                }

            }

        }
        Status.ERROR -> {
            Text("При получении информации о рейсах произошла ошибка", color = Color.Red)
            Log.d(LOG_TAG, resource.message?: "Нет сообщения")
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
//        elevation = 10.dp,
//        shape = RoundedCornerShape(20)
    ) {
        Row(
            Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Номер\nрейса", fontSize = 20.sp)
            Text("Пункт", fontSize = 20.sp)
            Text("Время", fontSize = 20.sp)
        }
    }
}

@Composable
fun FlightsList(modifier: Modifier = Modifier, flights: List<Flight>) {
    LazyColumn(
        modifier.padding(horizontal = 10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(flights) { flight ->
            FlightCard(Modifier.padding(bottom = 5.dp), flight = flight)
        }
    }
}

@Composable
fun FlightCard(modifier: Modifier = Modifier, flight: Flight){
    Card(
        modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(20)
    ) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(flight.thread!!.number ?: "???", fontSize = 25.sp)
            var title = flight.thread!!.shortTitle ?: ""
            title = title.replace("Москва","")
                .replace(" — ","")
                .replace("-", "-\n")
                .replace(" ", " \n")
            Text(title, fontSize = 20.sp, textAlign = TextAlign.Center)
            val dateString = flight.departure ?: flight.arrival ?: ""
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(dateString)?: Date()
            Text(SimpleDateFormat("HH:mm").format(date), fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightCard(flight = Flight(
        "2022-06-06T11:25:00+03:00",
        terminal = "B",
        thread = Thread(
            shortTitle = "Улан-Удэ — Москва",
            number = "5N 562"
        )
    ))
}

/*
@Preview(showBackground = true)
@Composable
fun MainPreview(){
    Main()
}*/
