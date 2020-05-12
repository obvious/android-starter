package `in`.obvious.android.starter.counter

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

class CounterScreen : Fragment() {

    private val loop: MobiusLoop.Builder<CounterModel, CounterEvent, CounterEffect> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        Mobius
            .loop(CounterUpdate(), CounterEffectHandler())
            .init(CounterInit())
    }

    private val controller: MobiusLoop.Controller<CounterModel, CounterEvent> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        MobiusAndroid
            .controller(loop, CounterModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            controller.connect(::connectEvents)
        }
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

    private fun connectEvents(events: Consumer<CounterEvent>): Connection<CounterModel> {
        // Set up UI event listeners here

        return object : Connection<CounterModel> {

            override fun accept(value: CounterModel) {
                // Update UI from the model
            }

            override fun dispose() {
                // Dispose event listeners
            }
        }
    }
}
