package `in`.obvious.android.starter.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Next.noChange
import com.spotify.mobius.Update

class CounterUpdate(
    private val maxAllowedCounterValue: Int
) : Update<CounterModel, CounterEvent, CounterEffect> {

    override fun update(
        model: CounterModel,
        event: CounterEvent
    ): Next<CounterModel, CounterEffect> {
        return when (event) {
            IncrementClicked -> incrementCounter(model)
            DecrementClicked -> next(model.decrement())
        }
    }

    private fun incrementCounter(model: CounterModel): Next<CounterModel, CounterEffect> {
        return if (model.counterValue < maxAllowedCounterValue)
            next(model.increment())
        else
            noChange()
    }
}
