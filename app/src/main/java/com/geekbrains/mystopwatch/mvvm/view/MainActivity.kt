package com.geekbrains.mystopwatch.mvvm.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.geekbrains.mystopwatch.R
import com.geekbrains.mystopwatch.mvvm.model.entities.TimestampProvider
import com.geekbrains.mystopwatch.mvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            println("VVV получение милисекунд : object TimestampProvider")
            return System.currentTimeMillis()
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.initTimer(timestampProvider)
        viewModel.getLiveData().observe(this) { time ->
            val textView = findViewById<TextView>(R.id.text_time)
            textView.text = time
        }
        findViewById<Button>(R.id.button_start).setOnClickListener {
            viewModel.startTimer()
        }
        findViewById<Button>(R.id.button_pause).setOnClickListener {
            viewModel.pauseTimer()
        }
        findViewById<Button>(R.id.button_stop).setOnClickListener {
            viewModel.stopTimer()
        }
    }
}



