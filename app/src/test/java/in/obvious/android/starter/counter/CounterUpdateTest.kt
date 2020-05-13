package `in`.obvious.android.starter.counter

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterUpdateTest {

    private val defaultModel = CounterModel()

    private val spec = UpdateSpec(CounterUpdate())

    @Test
    fun `when the increment button is clicked, the counter should be incremented`() {
        spec
            .given(defaultModel)
            .whenEvent(IncrementClicked)
            .then(
                assertThatNext(
                    hasModel(defaultModel.increment()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when the decrement button is clicked, the counter should be decremented`() {
        spec
            .given(defaultModel)
            .whenEvent(DecrementClicked)
            .then(
                assertThatNext(
                    hasModel(defaultModel.decrement()),
                    hasNoEffects()
                )
            )
    }
}
