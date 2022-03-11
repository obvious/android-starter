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
import `in`.obvious.android.starter.databinding.ScreenCounterBinding
import `in`.obvious.android.starter.mobius.MainThreadUiWorkRunner

class CounterScreen : Fragment() {

  private var _binding: ScreenCounterBinding? = null
  private val binding
    get() = _binding!!

  private val loop: MobiusLoop.Builder<CounterModel, CounterEvent, CounterEffect> by
    lazy(LazyThreadSafetyMode.NONE) {
      val update = CounterUpdate(minAllowedCounterValue = 0, maxAllowedCounterValue = 10)

      val countdownFactory =
        object : Countdown.Factory {
          override fun create(): Countdown {
            return PlatformCountdown()
          }
        }

      Mobius.loop(
          update,
          CounterEffectHandler(
            countdownFactory = countdownFactory,
            uiWorkRunner = MainThreadUiWorkRunner()
          )
        )
        .init(CounterInit())
    }

  private val controller: MobiusLoop.Controller<CounterModel, CounterEvent> by
    lazy(LazyThreadSafetyMode.NONE) { MobiusAndroid.controller(loop, CounterModel()) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = ScreenCounterBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    controller.connect { events -> connectEvents(events) }
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
    _binding = null
    super.onDestroyView()
  }

  private fun connectEvents(events: Consumer<CounterEvent>): Connection<CounterModel> {
    setupEvents(events)

    return object : Connection<CounterModel> {

      override fun accept(value: CounterModel) {
        render(value)
      }

      override fun dispose() {
        disposeEvents()
      }
    }
  }

  private fun setupEvents(events: Consumer<CounterEvent>) {
    binding.incrementCounter.setOnClickListener { events.accept(IncrementClicked) }
    binding.decrementCounter.setOnClickListener { events.accept(DecrementClicked) }
    binding.startCountdown.setOnClickListener { events.accept(StartCountdownClicked) }
  }

  private fun disposeEvents() {
    binding.incrementCounter.setOnClickListener(null)
    binding.decrementCounter.setOnClickListener(null)
    binding.startCountdown.setOnClickListener(null)
  }

  private fun render(model: CounterModel) {
    binding.counterLabel.text = model.counterValue.toString()
    if (model.isCountdownOngoing) {
      binding.incrementCounter.visibility = View.INVISIBLE
      binding.decrementCounter.visibility = View.INVISIBLE
      binding.startCountdown.isEnabled = false
    } else {
      binding.incrementCounter.visibility = View.VISIBLE
      binding.decrementCounter.visibility = View.VISIBLE
      binding.startCountdown.isEnabled = true
    }
  }
}
