package `in`.obvious.android.starter.counter

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterUpdateTest {

    @Test
    fun `when the increment button is clicked, the counter should be incremented`() {
        val spec = UpdateSpec(CounterUpdate())

        val model = CounterModel()

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
}
