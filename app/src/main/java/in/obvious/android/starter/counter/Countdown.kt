package `in`.obvious.android.starter.counter

import `in`.obvious.android.starter.util.Cancelable
import java.util.concurrent.TimeUnit

interface Countdown : Cancelable {
  fun start(tickAmount: Int, tickTimeUnit: TimeUnit, tick: () -> Unit, done: () -> Unit)

  interface Factory {
    fun create(): Countdown
  }
}
