package com.example.k_workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.k_workoutapp.databinding.ActivityEndBinding

class EndActivity : AppCompatActivity() {

    private var binding : ActivityEndBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            //THIS WILL ACTIVATE OUR BACK BUTTON
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            finish()
        }
    }
}