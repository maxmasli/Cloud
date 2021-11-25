package masli.prof.cloud

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class WeatherJsonConverterFactory : JsonDeserializer<WeatherData> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WeatherData {
        val weatherJsonObject = json?.asJsonObject
        val weatherObject = weatherJsonObject?.get("weather")?.asJsonArray?.get(0)?.asJsonObject

        //
        val weather = weatherObject?.get("main").toString().removeSuffix("\"").removePrefix("\"")
        //
        val city = weatherJsonObject?.get("name").toString().removeSuffix("\"").removePrefix("\"")
        //
        val temperature = weatherJsonObject?.get("main")?.asJsonObject?.get("temp").toString()
        //
        val pressure = weatherJsonObject?.get("main")?.asJsonObject?.get("pressure").toString()
        //
        val windSpeed = weatherJsonObject?.get("wind")?.asJsonObject?.get("speed").toString()

        return WeatherData(city = city, weather = weather, temperature = temperature, pressure = pressure, windSpeed = windSpeed)
    }

}