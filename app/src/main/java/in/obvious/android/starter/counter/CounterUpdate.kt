package `in`.obvious.android.starter.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.noChange
import com.spotify.mobius.Update

class CounterUpdate : Update<CounterModel, CounterEvent, CounterEffect> {

    override fun update(
        model: CounterModel,
        event: CounterEvent
    ): Next<CounterModel, CounterEffect> {
        return noChange()
    }
}
