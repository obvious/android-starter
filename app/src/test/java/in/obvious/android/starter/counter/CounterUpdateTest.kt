package `in`.obvious.android.starter.counter

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import java.util.concurrent.TimeUnit.SECONDS
import org.junit.Test

class CounterUpdateTest {

  private val maxAllowedCounterValue = 3
  private val minimumAllowedCounterValue = 0

  private val update =
    CounterUpdate(
      minAllowedCounterValue = minimumAllowedCounterValue,
      maxAllowedCounterValue = maxAllowedCounterValue
    )

  private val spec = UpdateSpec(update)

  @Test
  fun `when the increment button is clicked, the counter should be incremented`() {
    val model = CounterModel(2)

    spec
      .given(model)
      .whenEvent(IncrementClicked)
      .then(assertThatNext(hasModel(model.increment()), hasNoEffects()))
  }

  @Test
  fun `when the decrement button is clicked, the counter should be decremented`() {
    val model = CounterModel(2)

    spec
      .given(model)
      .whenEvent(DecrementClicked)
      .then(assertThatNext(hasModel(model.decrement()), hasNoEffects()))
  }

  @Test
  fun `when the increment button is clicked when the counter value is maximum allowed, the counter value must not change`() {
    val model = CounterModel(maxAllowedCounterValue)

    spec.given(model).whenEvent(IncrementClicked).then(assertThatNext(hasNothing()))
  }

  @Test
  fun `when the decrement button is clicked when the counter value is minimum allowed, the counter value must not change`() {
    val model = CounterModel(minimumAllowedCounterValue)

    spec.given(model).whenEvent(DecrementClicked).then(assertThatNext(hasNothing()))
  }

  @Test
  fun `when the countdown button is clicked, the countdown must be started`() {
    val counterValue = maxAllowedCounterValue
    val model = CounterModel(counterValue)

    spec
      .given(model)
      .whenEvent(StartCountdownClicked)
      .then(
        assertThatNext(
          hasModel(model.countdownStarted()),
          hasEffects(StartCountdown(counterValue, SECONDS) as CounterEffect)
        )
      )
  }

  @Test
  fun `when a countdown tick is received, the counter value should be decremented`() {
    val counterValue = 2
    val model = CounterModel(counterValue)

    spec
      .given(model)
      .whenEvent(CountdownTick)
      .then(assertThatNext(hasModel(model.decrement()), hasNoEffects()))
  }

  @Test
  fun `when the countdown is complete, the UI should be updated`() {
    val counterValue = 0
    val model = CounterModel(counterValue)

    spec
      .given(model)
      .whenEvent(CountdownComplete)
      .then(assertThatNext(hasModel(model.countdownEnded()), hasNoEffects()))
  }
}
