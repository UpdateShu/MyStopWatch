package com.geekbrains.mystopwatch.mvvm.model.data

import com.geekbrains.mystopwatch.mvvm.model.entities.StopwatchState
import com.geekbrains.mystopwatch.mvvm.model.entities.TimestampProvider
import com.geekbrains.mystopwatch.utils.ZERO_MARKER

class ElapsedTimeCalculator(private val timestampProvider: TimestampProvider)
{
    fun calculate(state: StopwatchState.Running): Long
        = state.elapsedTime + if (timestampProvider.getMilliseconds() > state.startTime) {
            timestampProvider.getMilliseconds() - state.startTime
        }
        else {
            ZERO_MARKER
        }
}