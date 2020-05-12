package `in`.obvious.android.starter.counter

import com.spotify.mobius.First
import com.spotify.mobius.First.first
import com.spotify.mobius.Init

class CounterInit : Init<CounterModel, CounterEffect> {

    override fun init(model: CounterModel): First<CounterModel, CounterEffect> {
        return first(model)
    }
}
