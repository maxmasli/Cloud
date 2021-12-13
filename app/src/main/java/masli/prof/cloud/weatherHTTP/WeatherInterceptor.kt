package masli.prof.cloud.weatherHTTP

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = "835f7f27dfa333487f7f7b65efc2cbf9"

class WeatherInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url().newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}