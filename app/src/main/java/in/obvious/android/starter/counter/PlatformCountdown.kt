package `in`.obvious.android.starter.counter

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

class PlatformCountdown : Countdown {

  private lateinit var timer: CountDownTimer

  override fun start(tickAmount: Int, tickTimeUnit: TimeUnit, tick: () -> Unit, done: () -> Unit) {
    timer = createTimer(tickAmount, tickTimeUnit, done, tick)

    timer.start()
  }

  private fun createTimer(
    tickAmount: Int,
    tickTimeUnit: TimeUnit,
    done: () -> Unit,
    tick: () -> Unit
  ): CountDownTimer {
    return object :
      CountDownTimer(
        MILLISECONDS.convert(tickAmount.toLong(), tickTimeUnit),
        MILLISECONDS.convert(1L, SECONDS)
      ) {

      override fun onFinish() {
        done()
      }

      override fun onTick(millisUntilFinished: Long) {
        tick()
      }
    }
  }

  override fun cancel() {
    if (::timer.isInitialized) {
      timer.cancel()
    }
  }
}
