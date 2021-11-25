package masli.prof.cloud.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import masli.prof.cloud.WeatherData
import masli.prof.cloud.WeatherFetcher

class WeatherViewModel : ViewModel() {

    val weatherData: LiveData<WeatherData>
    private val mutableSearchTerm = MutableLiveData<String>()
    private val weatherFetcher = WeatherFetcher()

    init {
        mutableSearchTerm.value = "kemerovo"
        weatherData = Transformations.switchMap(mutableSearchTerm){ searchTerm ->
            weatherFetcher.fetchData(searchTerm)
        }
    }

    fun fetchData(query: String){
        mutableSearchTerm.value = query
    }
}