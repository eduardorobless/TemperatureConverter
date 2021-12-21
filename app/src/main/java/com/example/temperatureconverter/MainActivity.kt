package com.example.temperatureconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.temperatureconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener { convertTemperature() }
    }


    private fun convertTemperature() {
        // Getting temperature value
        val temperature = binding.temperatureValueEditText.text.toString().toDoubleOrNull()
        if (temperature == null) {
            binding.conversionResult.text = getString(R.string.empty_value_error)
            return
        }
        // Getting origin unit
        val originUnit = when (binding.originUnitOptions.checkedRadioButtonId) {
            R.id.origin_celsius -> 'C'
            R.id.origin_fahrenheit -> 'F'
            else -> 'K'
        }
        // Getting destination unit
        val destinationUnit = when (binding.destinationUnitOptions.checkedRadioButtonId) {
            R.id.destination_celsius -> 'C'
            R.id.destination_fahrenheit -> 'F'
            else -> 'K'
        }

        if (originUnit == destinationUnit) {
            binding.conversionResult.text = getString(R.string.same_units_error)
        } else if (originUnit != 'K' && destinationUnit != 'K') {
            binding.conversionResult.text = getString(
                R.string.conversion_result,
                temperature.toString(), "°$originUnit", String.format(
                    "%.2f",
                    temperatureConversion(originUnit, destinationUnit, temperature)
                ),
                "°$destinationUnit"
            )
        } else if (originUnit == 'K' && destinationUnit != 'K') {
            binding.conversionResult.text = getString(
                R.string.conversion_result,
                temperature.toString(), "$originUnit", String.format(
                    "%.2f",
                    temperatureConversion(originUnit, destinationUnit, temperature)
                ),
                "°$destinationUnit"
            )
        } else if (destinationUnit == 'K' && originUnit != 'k') {
            binding.conversionResult.text = getString(
                R.string.conversion_result,
                temperature.toString(), "$originUnit", String.format(
                    "%.2f",
                    temperatureConversion(originUnit, destinationUnit, temperature)
                ),
                "$destinationUnit"
            )
        }

    }

    private fun temperatureConversion(
        origin: Char,
        destination: Char,
        temperatureVal: Double
    ): Double? {
        var temperature: Double? = null
        if (origin == 'C' && destination == 'K') {
            temperature = temperatureVal + 273
        } else if (origin == 'C' && destination == 'F') {
            temperature = temperatureVal * 9 / 5 + 32
        } else if (origin == 'F' && destination == 'C') {
            temperature = (temperatureVal - 32) * 5 / 9
        } else if (origin == 'F' && destination == 'K') {
            temperature = (temperatureVal - 32) * 5 / 9 + 273
        } else if (origin == 'K' && destination == 'C') {
            temperature = temperatureVal - 273
        } else if (origin == 'K' && destination == 'F') {
            temperature = (temperatureVal - 273) * 9 / 5 + 32
        }
        return temperature
    }

}
