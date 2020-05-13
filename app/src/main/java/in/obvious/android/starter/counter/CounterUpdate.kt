package `in`.obvious.android.starter.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update
import java.util.concurrent.TimeUnit.SECONDS

class CounterUpdate(
    private val minAllowedCounterValue: Int,
    private val maxAllowedCounterValue: Int
) : Update<CounterModel, CounterEvent, CounterEffect> {

    override fun update(
        model: CounterModel,
        event: CounterEvent
    ): Next<CounterModel, CounterEffect> {
        return when (event) {
            IncrementClicked -> incrementCounter(model)
            DecrementClicked -> decrementCounter(model)
            StartCountdownClicked -> next(
                model.countdownStarted(),
                setOf(StartCountdown(model.counterValue, SECONDS))
            )
            CountdownTick -> next(model.decrement())
            CountdownComplete -> next(model.countdownEnded())
        }
    }

    private fun incrementCounter(model: CounterModel): Next<CounterModel, CounterEffect> {
        return if (model.counterValue < maxAllowedCounterValue)
            next(model.increment())
        else
            noChange()
    }

    private fun decrementCounter(model: CounterModel): Next<CounterModel, CounterEffect> {
        return if (model.counterValue > minAllowedCounterValue)
            next(model.decrement())
        else
            noChange()
    }
}
