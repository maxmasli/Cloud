package masli.prof.cloud.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import masli.prof.cloud.R
import masli.prof.cloud.WeatherConverter
import masli.prof.cloud.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var cityNameTextView: AppCompatTextView
    private lateinit var temperatureTextView: AppCompatTextView
    private lateinit var weatherTextView: AppCompatTextView
    private lateinit var pressureTextView: AppCompatTextView
    private lateinit var windSpeedTextView: AppCompatTextView
    private lateinit var cityEditText: AppCompatEditText
    private lateinit var findButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        initViews(view)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        findButton.setOnClickListener {
            val query = cityEditText.text.toString()
            weatherViewModel.fetchData(query)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.weatherData.observe(viewLifecycleOwner, { data ->
            if(data != null){
                cityNameTextView.text = data.city
                temperatureTextView.text = WeatherConverter().convertTemperature(data.temperature).toString()
                weatherTextView.text = data.weather
                pressureTextView.text = data.pressure
                windSpeedTextView.text = data.windSpeed
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initViews(view: View) {
        cityNameTextView = view.findViewById(R.id.city_name)
        temperatureTextView = view.findViewById(R.id.temperature)
        weatherTextView = view.findViewById(R.id.weather)
        pressureTextView = view.findViewById(R.id.pressure_value)
        windSpeedTextView = view.findViewById(R.id.wind_speed_value)
        cityEditText = view.findViewById(R.id.city_et)
        findButton = view.findViewById(R.id.city_find_btn)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }
}