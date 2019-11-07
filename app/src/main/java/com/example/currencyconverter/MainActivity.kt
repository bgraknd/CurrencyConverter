package com.example.currencyconverter

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRates(view: View) {

        val downloadData = Download()

        try {

            val url = "http://data.fixer.io/api/latest?access_key=cd44d68daef81a54ca28f3280f0f2e93"
            downloadData.execute(url)

        } catch (e: Exception) {

        }
    }

    inner class Download : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {

            var result = ""

            var url: URL
            var httpURLConnection: HttpURLConnection

            try {

                url = URL(params[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {

                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()

                }
                return result

            } catch (e: Exception) {
                return result
            }
        }

        override fun onPostExecute(result: String?) {
            println(result)
            Toast.makeText(this@MainActivity, "BOK", Toast.LENGTH_LONG).show()

            try {

                val jsonObject = JSONObject(result)
                val base = jsonObject.getString("base")
                println(base)
                val rates = jsonObject.getString("rates")
                println(rates)

                val jsonObjectInRates = JSONObject(rates)
                val trlira = jsonObjectInRates.getString("TRY")
                val usd = jsonObjectInRates.getString("USD")
                val gbp = jsonObjectInRates.getString("GBP")
                val cny = jsonObjectInRates.getString("CNY")
                println(trlira)

                tryText.text = "TRY: " + trlira
                usdText.text = "USD: " + usd
                gbpText.text = "GBP: " + gbp
                cnyText.text = "CNY: " + cny

            } catch (e: Exception) {

            }

            super.onPostExecute(result)
        }
    }
}
