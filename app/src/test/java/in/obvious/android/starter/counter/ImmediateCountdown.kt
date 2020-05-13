package `in`.obvious.android.starter.counter

import java.util.concurrent.TimeUnit

class ImmediateCountdown : Countdown {

    var cancelled: Boolean = false

    override fun start(
        tickAmount: Int,
        tickTimeUnit: TimeUnit,
        tick: () -> Unit,
        done: () -> Unit
    ) {
        val totalSecondsToCount =
            TimeUnit.SECONDS.convert(tickAmount.toLong(), tickTimeUnit).toInt()

        repeat(totalSecondsToCount) { tick() }
        done()
    }

    override fun cancel() {
        cancelled = true
    }

    object Factories {

        fun exact(countdown: ImmediateCountdown): Countdown.Factory = object : Countdown.Factory {
            override fun create(): Countdown = countdown
        }

        fun createNew(): Countdown.Factory = object : Countdown.Factory {
            override fun create(): Countdown {
                return ImmediateCountdown()
            }
        }
    }
}
