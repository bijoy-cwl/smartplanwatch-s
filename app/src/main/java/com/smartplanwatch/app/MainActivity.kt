package com.smartplanwatch.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.smartplanwatch.presentation.model.WatchRes
import com.smartplanwatch.app.Constants.baseUrl
import com.smartplanwatch.app.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    private lateinit var textViewTodayNumber: TextView
    private lateinit var textViewBeforeYesterdayNumber: TextView
    private lateinit var textViewYesterdayNumber: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTodayNumber = findViewById(R.id.textViewTodayNumber)
        textViewYesterdayNumber = findViewById(R.id.textViewYesterdayNumber)
        textViewBeforeYesterdayNumber = findViewById(R.id.textViewBeforeYesterdayNumber)
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        apiService = retrofit.create(ApiService::class.java)

        // Make the API call
        val call = apiService.getWatchData()
        call.enqueue(object : Callback<WatchRes> {
            override fun onResponse(call: Call<WatchRes>, response: Response<WatchRes>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val data = response.body()
                    // Do something with the data
                    if (data != null) {
                        Log.e("today","Today: "+data.today)
                        textViewTodayNumber.text = "${data.today}"
                        textViewYesterdayNumber.text = "${data.yesterday}"
                        textViewBeforeYesterdayNumber.text = "${data.day_before_yesterday}"
                    }

                } else {
                    Log.e("Error"," error: "+response.message())
                    // Handle the error response
                }
            }

            override fun onFailure(call: Call<WatchRes>, t: Throwable) {
                // Handle failure
                Log.e("Error Fail"," error: "+t.message)
                t.printStackTrace();
            }
        })
    }
}