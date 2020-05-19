package `in`.obvious.android.starter.counter

import `in`.obvious.android.starter.mobius.UiWorkRunner
import `in`.obvious.android.starter.util.Cancelable
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

class CounterEffectHandler(
    private val countdownFactory: Countdown.Factory,
    private val uiWorkRunner: UiWorkRunner
) : Connectable<CounterEffect, CounterEvent> {

    private var cancellables: List<Cancelable> = emptyList()

    override fun connect(events: Consumer<CounterEvent>): Connection<CounterEffect> {
        return object : Connection<CounterEffect> {

            override fun accept(effect: CounterEffect) {
                when (effect) {
                    is StartCountdown -> startCountdown(effect, events)
                }
            }

            override fun dispose() {
                cancellables.forEach(Cancelable::cancel)
            }
        }
    }

    private fun startCountdown(
        effect: StartCountdown,
        events: Consumer<CounterEvent>
    ) {
        uiWorkRunner.post {
            val countdown = countdownFactory.create()

            countdown.start(
                tickAmount = effect.ticks,
                tickTimeUnit = effect.timeUnit,
                tick = { events.accept(CountdownTick) },
                done = {
                    events.accept(CountdownComplete)
                    removeCancelable(countdown)
                }
            )

            addCancellable(countdown)
        }
    }

    private fun addCancellable(cancelable: Cancelable) {
        cancellables = cancellables + cancelable
    }

    private fun removeCancelable(cancelable: Cancelable) {
        cancellables = cancellables - cancelable
    }
}
