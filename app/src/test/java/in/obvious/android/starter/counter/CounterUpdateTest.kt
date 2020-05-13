package `in`.obvious.android.starter.counter

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterUpdateTest {

    private val maxAllowedCounterValue = 3

    private val update = CounterUpdate(maxAllowedCounterValue = maxAllowedCounterValue)

    private val spec = UpdateSpec(update)

    @Test
    fun `when the increment button is clicked, the counter should be incremented`() {
        val model = CounterModel(2)

        spec
            .given(model)
            .whenEvent(IncrementClicked)
            .then(
                assertThatNext(
                    hasModel(model.increment()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when the decrement button is clicked, the counter should be decremented`() {
        val model = CounterModel(2)

        spec
            .given(model)
            .whenEvent(DecrementClicked)
            .then(
                assertThatNext(
                    hasModel(model.decrement()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when the increment button is clicked when the counter value is maximum allowed, the counter value must not change`() {
        val model = CounterModel(0)
            .increment()
            .increment()
            .increment()

        spec
            .given(model)
            .whenEvent(IncrementClicked)
            .then(assertThatNext(hasNothing()))
    }
}
