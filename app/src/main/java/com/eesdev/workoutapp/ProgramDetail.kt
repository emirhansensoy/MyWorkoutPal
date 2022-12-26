package com.eesdev.workoutapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://youtube.googleapis.com/youtube/v3/"

class ProgramDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_detail)

       val db = DatabaseHandler(this,null)

        val exerciseName = intent.getStringExtra("exerciseName")
        val exerciseNameTextView = findViewById<TextView>(R.id.exerciseName)
        exerciseNameTextView.text = exerciseName.toString()

        val exerciseDescriptionTextView = findViewById<TextView>(R.id.exerciseDescription)
        exerciseDescriptionTextView.text = db.getExerciseDesc(exerciseName.toString())

        getYoutubeData(exerciseName.toString())

        val clickToSeeTextView = findViewById<TextView>(R.id.clickToSee)
        clickToSeeTextView.text = "Click here to watch how to perform " + exerciseName.toString()

        val videoUrl = findViewById<TextView>(R.id.videoUrl)
        videoUrl.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl.text.toString()))
            startActivity(browserIntent)
        }

    }

    private fun getYoutubeData(exerciseName: String)
    {
        val value="search?part=snippet&q=" + exerciseName.replace(" ","") + "&key=AIzaSyA0Xj7z9A_N-aYoywLXe01jKSvGA_ttR_E"
        val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(value)

        retrofitData.enqueue(object : Callback<YoutubeData?> {

            override fun onResponse(
                call: Call<YoutubeData?>,
                response: Response<YoutubeData?>
            ) {
                val responseBody = response.body()!!

                val videoUrl = findViewById<TextView>(R.id.videoUrl)
                videoUrl.text = "https://www.youtube.com/watch?v=" + responseBody.items[0].id.videoId
            }

            override fun onFailure(call: Call<YoutubeData?>, t: Throwable) {
                Log.d("ProgramDetail", "onFailure: " + t.message)
            }
        })
    }
}