package com.example.mycalculator

import MyAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import kotlin.Exception

class ExchangeRate : AppCompatActivity() { // Defines the ExchangeRate class, inheriting from AppCompatActivity

    var baseCurrency = "EUR" // Declares a variable for the base currency with a default value
    var convertedToCurrency = "USD" // Declares a variable for the converted currency with a default value
    var conversionRate = 0f // Declares a variable for the conversion rate with a default value

    @OptIn(DelicateCoroutinesApi::class) // Marks the method as using delicate coroutines API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rate)

        // Initializes an array of image URLs for currency flags
        val ImageUrl= arrayOf("https://cdn.countryflags.com/thumbs/australia/flag-400.png",
            "https://www.countryflags.com/wp-content/uploads/bulgaria-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/brazil-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/canada-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/switzerland-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/china-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/czech-republic-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/denmark-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/united-kingdom-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/hongkong-flag-jpg-xl.jpg",
            "https://www.countryflags.com/wp-content/uploads/hungary-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/indonesia-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/israel-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/india-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/iceland-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/japan-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/south-korea-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/mexico-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/malaysia-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/norway-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/new-zealand-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/philippines-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/poland-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/romania-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/sweden-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/singapore-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/thailand-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/turkey-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/united-states-of-america-flag-png-large.png",
            "https://www.countryflags.com/wp-content/uploads/south-africa-flag-png-large.png",)

        val recyclerView: RecyclerView = findViewById(R.id.CurrencyFlagRecycleView) // Initializes RecyclerView

        val API ="https://api.frankfurter.app/latest?" // Defines the base API URL for exchange rates

        GlobalScope.launch(Dispatchers.IO) { // Launches a coroutine in the IO context
            try { // Starts a try block to catch exceptions
                val apiResult = URL(API).readText() // Reads text from the API URL
                val jsonObject = JSONObject(apiResult) // Creates a JSONObject from the API result
                val ratesObject = jsonObject.getJSONObject("rates") // Gets the "rates" object from JSON

                val rateList = mutableListOf<String>() // Initializes a mutable list for currency rates
                ratesObject.keys().forEach { currency -> // Iterates through each currency key in ratesObject
                    val rate = "${currency}: ${ratesObject.getDouble(currency)}" // Formats the currency rate
                    rateList.add(rate) // Adds the formatted rate to the list
                    Log.d("Main3",rateList[0]) // Logs the first rate in the list
                }

                withContext(Dispatchers.Main) { // Switches to the Main coroutine context
                    recyclerView.adapter = MyAdapter(rateList.toTypedArray(), ImageUrl) // Sets adapter for RecyclerView
                }
            } catch (e: Exception) { // Catches any exceptions thrown in the try block
                Log.e("Main", "$e") // Logs the exception message
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this) // Sets layout manager for RecyclerView

        spinnerSetup() // Calls a function to setup spinners
        textChangedStuff() // Calls a function to setup text change listener
    }

    private fun textChangedStuff() { // Defines a function for setting up text change listener
        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1) // Initializes EditText widget

        et_firstConversion.addTextChangedListener(object : TextWatcher { // Adds text change listener to EditText
            override fun afterTextChanged(s: Editable?) { // Executes after text is changed
                try { // Starts a try block to catch exceptions
                    getApiResult() // Calls a function to get API result
                } catch (e: Exception) { // Catches any exceptions thrown
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show() // Shows a toast message
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main", "OnTextChanged")
            }
        })
    }

    private fun getApiResult() { // Defines a function to fetch API result
        // Get references to EditText fields
        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1)
        val et_secondConversion=findViewById<EditText>(R.id.GetExchangeRate2)

        // Check if EditText is not empty
        if (et_firstConversion != null && et_firstConversion.text.isNotEmpty() && et_firstConversion.text.isNotBlank()) {
            // Build API URL based on selected currencies
            val API ="https://api.frankfurter.app/latest?base=$baseCurrency&symbols=$convertedToCurrency"

            // Check if base and converted currencies are different
            if (baseCurrency == convertedToCurrency) {
                Toast.makeText(applicationContext, "Please pick a currency to convert", Toast.LENGTH_SHORT).show()
            } else {
                GlobalScope.launch(Dispatchers.IO) { // Launches a coroutine in the IO context
                    try { // Starts a try block to catch exceptions
                        // Fetch API result and calculate conversion rate
                        val apiResult = URL(API).readText() // Reads text from the API URL
                        val jsonObject = JSONObject(apiResult) // Creates a JSONObject from the API result
                        conversionRate =
                            jsonObject.getJSONObject("rates").getString(convertedToCurrency)
                                .toFloat() // Parses the conversion rate from JSON

                        withContext(Dispatchers.Main) { // Switches to the Main coroutine context
                            // Calculates converted value and sets it to second EditText
                            val text =
                                ((et_firstConversion.text.toString()
                                    .toFloat()) * conversionRate).toString()
                            et_secondConversion?.setText(text)
                        }
                    } catch (e: Exception) { // Catches any exceptions thrown in the try block
                        Log.e("Main", "$e") // Logs the exception message
                    }
                }
            }
        }
    }

    private fun spinnerSetup() { // Defines a function to setup spinners
        val spinner: Spinner = findViewById(R.id.CurrencySpinner1) // Initializes the first spinner
        val spinner2: Spinner = findViewById(R.id.CurrencySpinner2) // Initializes the second spinner

        // Create ArrayAdapter for spinners with currency codes
        ArrayAdapter.createFromResource(
            this,
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Set adapter for first spinner
            spinner.adapter = adapter
            spinner.setSelection(adapter.getPosition("EUR")) // Set default selection to EUR
        }

        // Create ArrayAdapter for second spinner with currency codes
        ArrayAdapter.createFromResource(
            this,
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Set adapter for second spinner
            spinner2.adapter = adapter
            spinner2.setSelection(adapter.getPosition("USD")) // Set default selection to USD
        }

        // Set item selection listener for first spinner
        spinner.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                // Update API result when base currency is changed
                getApiResult()
            }
        })

        // Set item selection listener for second spinner
        spinner2.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                // Update API result when converted currency is changed
                getApiResult()
            }
        })
    }
}
