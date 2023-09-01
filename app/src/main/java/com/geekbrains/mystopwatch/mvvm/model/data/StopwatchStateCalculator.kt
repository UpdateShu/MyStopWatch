package com.geekbrains.mystopwatch.mvvm.model.data

import com.geekbrains.mystopwatch.mvvm.model.entities.StopwatchState
import com.geekbrains.mystopwatch.mvvm.model.entities.TimestampProvider

class StopwatchStateCalculator(
    private val timestampProvider: TimestampProvider,
    val elapsedTimeCalculator: ElapsedTimeCalculator)
{
    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState

            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }
}