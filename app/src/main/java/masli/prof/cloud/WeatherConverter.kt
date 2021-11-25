package masli.prof.cloud

import kotlin.math.roundToInt

class WeatherConverter {

    fun convertTemperature(temp: String): Double {
        val tempF = temp.toFloat()
        val celsius = tempF-273.15
        return (celsius*10).roundToInt()/10.0
    }
}