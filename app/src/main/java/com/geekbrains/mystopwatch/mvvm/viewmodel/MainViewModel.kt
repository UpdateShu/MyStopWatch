package com.geekbrains.mystopwatch.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.geekbrains.mystopwatch.mvvm.model.data.ElapsedTimeCalculator
import com.geekbrains.mystopwatch.mvvm.model.data.StopwatchStateCalculator
import com.geekbrains.mystopwatch.mvvm.model.data.StopwatchStateHolder
import com.geekbrains.mystopwatch.mvvm.model.entities.TimestampProvider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainViewModel constructor(
    private val liveData: MutableLiveData<String> = MutableLiveData()) : ViewModel() {

    fun getLiveData(): LiveData<String> = liveData

    private lateinit var stopwatchListOrchestrator : StopwatchListOrchestrator

    fun initTimer(timestampProvider : TimestampProvider) {
        stopwatchListOrchestrator = StopwatchListOrchestrator(
            CoroutineScope(Dispatchers.Main + SupervisorJob()),
            StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider)
                )
            )
        )
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                liveData.postValue(it)
            }
        }
    }

    fun startTimer() = stopwatchListOrchestrator.start()

    fun pauseTimer() = stopwatchListOrchestrator.pause()

    fun stopTimer() = stopwatchListOrchestrator.stop()
}