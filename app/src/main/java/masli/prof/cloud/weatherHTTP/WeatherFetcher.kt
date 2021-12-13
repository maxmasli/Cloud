package masli.prof.cloud.weatherHTTP

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import masli.prof.cloud.WeatherData
import masli.prof.cloud.api.WeatherApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherFetcher {
    private val weatherApi: WeatherApi
    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(WeatherInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(
                WeatherData::class.java, WeatherJsonConverterFactory()
            ).create()))
            .client(client)
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun fetchData(query: String): LiveData<WeatherData> {
        val weatherLivedata = MutableLiveData<WeatherData>()

        weatherApi.fetchData(query).enqueue(object: Callback<WeatherData>{
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val weatherResponse = response.body()
                weatherLivedata.value = weatherResponse
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("TAG", "ERROR")
            }
        })
        return weatherLivedata
    }
}