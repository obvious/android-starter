package `in`.obvious.android.starter.counter

import `in`.obvious.android.starter.R
import `in`.obvious.android.starter.mobius.MainThreadUiWorkRunner
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import kotlinx.android.synthetic.main.screen_counter.*
import kotlinx.android.synthetic.main.screen_counter.view.*
import kotlinx.android.synthetic.main.screen_counter.view.startCountdown

class CounterScreen : Fragment() {

    private val loop: MobiusLoop.Builder<CounterModel, CounterEvent, CounterEffect> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        val update = CounterUpdate(
            minAllowedCounterValue = 0,
            maxAllowedCounterValue = 10
        )

        val countdownFactory = object : Countdown.Factory {
            override fun create(): Countdown {
                return PlatformCountdown()
            }
        }

        Mobius
            .loop(update, CounterEffectHandler(
                countdownFactory = countdownFactory,
                uiWorkRunner = MainThreadUiWorkRunner()
            ))
            .init(CounterInit())
    }

    private val controller: MobiusLoop.Controller<CounterModel, CounterEvent> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        MobiusAndroid.controller(loop, CounterModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.screen_counter, container, false)

        controller.connect { events -> connectEvents(view, events) }

        return view
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        controller.stop()
        super.onPause()
    }

    override fun onDestroyView() {
        controller.disconnect()
        super.onDestroyView()
    }

    private fun connectEvents(
        rootView: View,
        events: Consumer<CounterEvent>
    ): Connection<CounterModel> {
        setupEvents(rootView, events)

        return object : Connection<CounterModel> {

            override fun accept(value: CounterModel) {
                render(rootView, value)
            }

            override fun dispose() {
                disposeEvents(rootView)
            }
        }
    }

    private fun setupEvents(view: View, events: Consumer<CounterEvent>) {
        view.incrementCounter.setOnClickListener { events.accept(IncrementClicked) }
        view.decrementCounter.setOnClickListener { events.accept(DecrementClicked) }
        view.startCountdown.setOnClickListener { events.accept(StartCountdownClicked) }
    }

    private fun disposeEvents(view: View) {
        view.incrementCounter.setOnClickListener(null)
        view.decrementCounter.setOnClickListener(null)
        view.startCountdown.setOnClickListener(null)
    }

    private fun render(view: View, model: CounterModel) {
        view.counterLabel.text = model.counterValue.toString()
        if (model.isCountdownOngoing) {
            view.incrementCounter.visibility = View.INVISIBLE
            view.decrementCounter.visibility = View.INVISIBLE
            startCountdown.isEnabled = false
        } else {
            view.incrementCounter.visibility = View.VISIBLE
            view.decrementCounter.visibility = View.VISIBLE
            startCountdown.isEnabled = true
        }
    }
}
