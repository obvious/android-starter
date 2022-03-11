package `in`.obvious.android.starter.counter

import java.util.concurrent.TimeUnit

class DoNothingCountdown : Countdown {

  var cancelled: Boolean = false

  override fun start(tickAmount: Int, tickTimeUnit: TimeUnit, tick: () -> Unit, done: () -> Unit) {
    // Nothing to do here
  }

  override fun cancel() {
    cancelled = true
  }

  object Factories {
    fun exact(countdown: DoNothingCountdown): Countdown.Factory {
      return object : Countdown.Factory {
        override fun create(): Countdown {
          return countdown
        }
      }
    }
  }
}
