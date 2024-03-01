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

class ExchangeRate : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rate)


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
        val recyclerView:RecyclerView=findViewById(R.id.CurrencyFlagRecycleView)


        val API ="https://api.frankfurter.app/latest?"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiResult = URL(API).readText()
                val jsonObject = JSONObject(apiResult)
                val ratesObject = jsonObject.getJSONObject("rates")

                val rateList = mutableListOf<String>()
                ratesObject.keys().forEach { currency ->
                    val rate = "${currency}: ${ratesObject.getDouble(currency)}"
                    rateList.add(rate)
                    Log.d("Main3",rateList[0])
                }

                withContext(Dispatchers.Main) {
                    recyclerView.adapter = MyAdapter(rateList.toTypedArray(), ImageUrl)
                }
            } catch (e: Exception) {
                Log.e("Main", "$e")
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        spinnerSetup()
        textChangedStuff()
    }

    private fun textChangedStuff() {

        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1)

                et_firstConversion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show()
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

    private fun getApiResult() {
        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1)
        val et_secondConversion=findViewById<EditText>(R.id.GetExchangeRate2)

        if (et_firstConversion != null && et_firstConversion.text.isNotEmpty() && et_firstConversion.text.isNotBlank()) {

            var API ="https://api.frankfurter.app/latest?base=$baseCurrency&symbols=$convertedToCurrency"

            if (baseCurrency == convertedToCurrency) {
                Toast.makeText(
                    applicationContext,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                GlobalScope.launch(Dispatchers.IO) {

                    try {

                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)
                        conversionRate =
                            jsonObject.getJSONObject("rates").getString(convertedToCurrency)
                                .toFloat()

                        Log.d("Main3", jsonObject.getJSONObject("rates").toString())
                        Log.d("Main1", "$conversionRate")
                        Log.d("Main2", apiResult)

                        withContext(Dispatchers.Main) {
                            val text =
                                ((et_firstConversion.text.toString()
                                    .toFloat()) * conversionRate).toString()
                            et_secondConversion?.setText(text)

                        }

                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }
    }

    private fun spinnerSetup() {
        val spinner: Spinner = findViewById(R.id.CurrencySpinner1)
        val spinner2: Spinner = findViewById(R.id.CurrencySpinner2)

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
            spinner.setSelection(adapter.getPosition("EUR"))

        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner2.adapter = adapter
            spinner2.setSelection(adapter.getPosition("USD"))


        }

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
                getApiResult()
            }

        })

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
                getApiResult()
            }

        })
    }
}


/*
package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.URL
import kotlin.Exception


class ExchangeRate : AppCompatActivity() {

    var baseCurrency="EUR"
    var convertedToCurrency="USD"
    var conversionRate= 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rate)

        val ButtonExchange:Button=findViewById(R.id.ExchangeNowButton)

        ButtonExchange.setOnClickListener{
            getAPIResult()

        }

        spinnerSetup()
        changeText()



    }

    private fun changeText() {
        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1)
        val et_SecoundConversion=findViewById<EditText>(R.id.GetExchangeRate2)

        et_firstConversion.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main","Before Text Change")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main","On Text Change")
            }

            override fun afterTextChanged(s: Editable?) {

                try{
                    getAPIResult()

                }catch (e:Exception){
                    Log.e("Main","$e")
                }
            }

        })



    }

    private fun spinnerSetup() {

        val spinner1:Spinner=findViewById(R.id.CurrencySpinner1)
        val spinner2:Spinner=findViewById(R.id.CurrencySpinner2)


        ArrayAdapter.createFromResource(this, R.array.currency_codes,android.R.layout.simple_spinner_item).also { adapter->adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter=adapter
        }

        ArrayAdapter.createFromResource(this, R.array.currency_codes,android.R.layout.simple_spinner_item).also { adapter->adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter=adapter
        }


        spinner1.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency=parent?.getItemAtPosition(position).toString()
                getAPIResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        })

        spinner2.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency=parent?.getItemAtPosition(position).toString()
                getAPIResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        })

    }

    fun getAPIResult(){
        val et_firstConversion=findViewById<EditText>(R.id.GetExchangeRate1)
        val et_SecoundConversion=findViewById<EditText>(R.id.GetExchangeRate2)


        if(et_firstConversion!=null && et_firstConversion.text.isEmpty() && et_firstConversion.text.isNotBlank()){
            val API="https://api.ratesapi.io/api/latest?base=$baseCurrency&symbols=$conversionRate"

            if(baseCurrency==convertedToCurrency){
                Toast.makeText(applicationContext,"Can Not Convert same Currency",Toast.LENGTH_LONG ).show()
            }else{
                GlobalScope.launch(Dispatchers.IO) {

                    try{
                    val apiResult:String=URL(API).readText()
                    val jsonObject=JSONObject(apiResult)
                    conversionRate=jsonObject.getJSONObject("rates").getString(convertedToCurrency).toFloat()

                    Log.d("Main","$conversionRate")
                    Log.d("Main",apiResult)

                    withContext(Dispatchers.Main){
                        val text=((et_firstConversion.text.toString().toFloat()) * conversionRate).toString()

                        et_SecoundConversion?.setText(text)


                    }


                }catch (e:Exception){

                    Log.e("Main","$e")

                    }
                }
            }

        }
    }


}*/
