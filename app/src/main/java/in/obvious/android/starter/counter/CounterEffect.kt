package `in`.obvious.android.starter.counter

import java.util.concurrent.TimeUnit

sealed class CounterEffect

data class StartCountdown(val ticks: Int, val timeUnit: TimeUnit) : CounterEffect()
