package `in`.obvious.android.starter.counter

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

class CounterEffectHandler<F, E> : Connectable<F, E> {

    override fun connect(output: Consumer<E>): Connection<F> {
        return object : Connection<F> {

            override fun accept(value: F) {}

            override fun dispose() {}
        }
    }
}
