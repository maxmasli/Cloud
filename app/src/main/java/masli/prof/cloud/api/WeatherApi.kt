package masli.prof.cloud.api

import masli.prof.cloud.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    fun fetchData(@Query("q") city: String): Call<WeatherData>
}