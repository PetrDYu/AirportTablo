package ru.petr.airporttablo.data.models

import com.google.gson.annotations.SerializedName


data class FlightsInfo (

    @SerializedName("date"              ) var date             : String?             = null,
    @SerializedName("event"             ) var event            : String?             = null,
    @SerializedName("interval_schedule" ) var intervalSchedule : ArrayList<String>   = arrayListOf(),
    @SerializedName("pagination"        ) var pagination       : Pagination?         = Pagination(),
    @SerializedName("schedule"          ) var flights          : ArrayList<Flight>   = arrayListOf(),
    @SerializedName("station"           ) var station          : Station?            = Station()

)

data class Flight (

    @SerializedName("arrival"     ) var arrival    : String?  = null,
    @SerializedName("days"        ) var days       : String?  = null,
    @SerializedName("departure"   ) var departure  : String?  = null,
    @SerializedName("except_days" ) var exceptDays : String?  = null,
    @SerializedName("is_fuzzy"    ) var isFuzzy    : Boolean? = null,
    @SerializedName("platform"    ) var platform   : String?  = null,
    @SerializedName("stops"       ) var stops      : String?  = null,
    @SerializedName("terminal"    ) var terminal   : String?  = null,
    @SerializedName("thread"      ) var thread     : Thread?  = Thread()

)

data class Pagination (

    @SerializedName("limit"  ) var limit  : Int? = null,
    @SerializedName("offset" ) var offset : Int? = null,
    @SerializedName("total"  ) var total  : Int? = null

)

data class Codes (

    @SerializedName("iata"   ) var iata   : String? = null,
    @SerializedName("icao"   ) var icao   : String? = null,
    @SerializedName("sirena" ) var sirena : String? = null

)

data class Carrier (

    @SerializedName("code"  ) var code  : Int?    = null,
    @SerializedName("codes" ) var codes : Codes?  = Codes(),
    @SerializedName("title" ) var title : String? = null

)

data class TransportSubtype (

    @SerializedName("code"  ) var code  : String? = null,
    @SerializedName("color" ) var color : String? = null,
    @SerializedName("title" ) var title : String? = null

)

data class Thread (

    @SerializedName("carrier"           ) var carrier          : Carrier?          = Carrier(),
    @SerializedName("express_type"      ) var expressType      : String?           = null,
    @SerializedName("number"            ) var number           : String?           = null,
    @SerializedName("short_title"       ) var shortTitle       : String?           = null,
    @SerializedName("title"             ) var title            : String?           = null,
    @SerializedName("transport_subtype" ) var transportSubtype : TransportSubtype? = TransportSubtype(),
    @SerializedName("transport_type"    ) var transportType    : String?           = null,
    @SerializedName("uid"               ) var uid              : String?           = null,
    @SerializedName("vehicle"           ) var vehicle          : String?           = null

)

data class Station (

    @SerializedName("code"              ) var code            : String? = null,
    @SerializedName("popular_title"     ) var popularTitle    : String? = null,
    @SerializedName("short_title"       ) var shortTitle      : String? = null,
    @SerializedName("station_type"      ) var stationType     : String? = null,
    @SerializedName("station_type_name" ) var stationTypeName : String? = null,
    @SerializedName("title"             ) var title           : String? = null,
    @SerializedName("transport_type"    ) var transportType   : String? = null,
    @SerializedName("type"              ) var type            : String? = null

)
