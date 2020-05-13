package `in`.obvious.android.starter.counter

import com.spotify.mobius.test.RecordingConsumer
import org.junit.Test
import java.util.concurrent.TimeUnit.SECONDS

class CounterEffectHandlerTest {

    @Test
    fun `when the start countdown effect is received, the countdown must be started`() {
        // given
        val effectHandler =
            CounterEffectHandler(countdownFactory = ImmediateCountdown.Factories.createNew())
        val receivedEvents = RecordingConsumer<CounterEvent>()
        val connection = effectHandler.connect(receivedEvents)

        // when
        connection.accept(StartCountdown(3, SECONDS))

        // then
        receivedEvents.assertValues(CountdownTick, CountdownTick, CountdownTick, CountdownComplete)
    }
}
