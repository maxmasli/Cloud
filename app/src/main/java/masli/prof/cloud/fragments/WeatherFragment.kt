package masli.prof.cloud.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.facebook.shimmer.ShimmerFrameLayout
import masli.prof.cloud.R
import masli.prof.cloud.WeatherConverter
import masli.prof.cloud.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var cityNameTextView: AppCompatTextView
    private lateinit var temperatureTextView: AppCompatTextView
    private lateinit var weatherTextView: AppCompatTextView
    private lateinit var pressureTextView: AppCompatTextView
    private lateinit var pressureTextTextView: AppCompatTextView
    private lateinit var windSpeedTextView: AppCompatTextView
    private lateinit var windSpeedTextTextView: AppCompatTextView
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
            showShimmer()
            hideKeyboard()
            val query = cityEditText.text.toString()
            weatherViewModel.fetchData(query)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.weatherData.observe(viewLifecycleOwner, { data ->
            if(data != null){
                //TODO подправить шимер он кривой чуток
                cityNameTextView.text = data.city
                temperatureTextView.text = WeatherConverter().convertTemperatureToString(data.temperature)
                weatherTextView.text = data.weather
                pressureTextView.text = data.pressure
                windSpeedTextView.text = data.windSpeed
                showViews()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initViews(view: View) {
        shimmer = view.findViewById(R.id.shimmer)
        cityNameTextView = view.findViewById(R.id.city_name)
        temperatureTextView = view.findViewById(R.id.temperature)
        weatherTextView = view.findViewById(R.id.weather)
        pressureTextView = view.findViewById(R.id.pressure_value)
        pressureTextTextView = view.findViewById(R.id.pressure_text)
        windSpeedTextView = view.findViewById(R.id.wind_speed_value)
        windSpeedTextTextView = view.findViewById(R.id.wind_speed_text)
        cityEditText = view.findViewById(R.id.city_et)
        findButton = view.findViewById(R.id.city_find_btn)
    }

    private fun hideKeyboard(){
        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showViews() {
        shimmer.stopShimmer()
        shimmer.hideShimmer()
        shimmer.visibility = View.GONE

        cityNameTextView.visibility = View.VISIBLE
        temperatureTextView.visibility = View.VISIBLE
        weatherTextView.visibility = View.VISIBLE
        pressureTextView.visibility = View.VISIBLE
        pressureTextTextView.visibility = View.VISIBLE
        windSpeedTextView.visibility = View.VISIBLE
        windSpeedTextTextView.visibility = View.VISIBLE
    }

    private fun showShimmer(){
        shimmer.visibility = View.VISIBLE
        shimmer.showShimmer(true)

        cityNameTextView.visibility = View.GONE
        temperatureTextView.visibility = View.GONE
        weatherTextView.visibility = View.GONE
        pressureTextView.visibility = View.GONE
        pressureTextTextView.visibility = View.GONE
        windSpeedTextView.visibility = View.GONE
        windSpeedTextTextView.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }
}