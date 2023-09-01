package com.geekbrains.mystopwatch.mvvm.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.geekbrains.mystopwatch.R
import com.geekbrains.mystopwatch.mvvm.model.data.ElapsedTimeCalculator
import com.geekbrains.mystopwatch.mvvm.model.data.StopwatchStateCalculator
import com.geekbrains.mystopwatch.mvvm.model.data.StopwatchStateHolder

import com.geekbrains.mystopwatch.mvvm.model.entities.TimestampProvider
import com.geekbrains.mystopwatch.mvvm.viewmodel.StopwatchListOrchestrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            println("VVV получение милисекунд : object TimestampProvider")
            return System.currentTimeMillis()
        }
    }
    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_time)
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                textView.text = it
            }
        }

        findViewById<Button>(R.id.button_start).setOnClickListener {
            stopwatchListOrchestrator.start()
        }
        findViewById<Button>(R.id.button_pause).setOnClickListener {
            stopwatchListOrchestrator.pause()
        }
        findViewById<Button>(R.id.button_stop).setOnClickListener {
            stopwatchListOrchestrator.stop()
        }
    }
}