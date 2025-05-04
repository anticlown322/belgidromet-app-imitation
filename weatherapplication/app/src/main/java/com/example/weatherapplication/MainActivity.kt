package com.example.weatherapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment.newInstance()
            )
            .commit()
    }
}