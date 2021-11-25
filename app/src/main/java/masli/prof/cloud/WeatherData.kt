package masli.prof.cloud

data class WeatherData(
    val city: String = "",
    val temperature: String = "",
    val weather: String = "",
    val pressure: String = "",
    val windSpeed: String = ""
) {
    fun isNotNull(): Boolean {
        return city.isNotBlank()
    }
}
