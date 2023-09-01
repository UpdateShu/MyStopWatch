package com.geekbrains.mystopwatch.mvvm.model.data

import com.geekbrains.mystopwatch.mvvm.model.entities.StopwatchState
import com.geekbrains.mystopwatch.utils.TimestampMillisecondsFormatter
import com.geekbrains.mystopwatch.utils.ZERO_MARKER

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator)
{
    private val timestampMillisecondsFormatter = TimestampMillisecondsFormatter()

    var currentState: StopwatchState = StopwatchState.Paused(ZERO_MARKER)
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(ZERO_MARKER)
    }

    fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> stopwatchStateCalculator.elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}