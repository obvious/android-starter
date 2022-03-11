package `in`.obvious.android.starter.counter

import com.google.common.truth.Truth.assertThat
import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import `in`.obvious.android.starter.ImmediateUiWorkRunner
import java.util.concurrent.TimeUnit.SECONDS
import org.junit.Test

class CounterEffectHandlerTest {

  private val receivedEvents = RecordingConsumer<CounterEvent>()

  @Test
  fun `when the start countdown effect is received, the countdown must be started`() {
    // given
    val countdownFactory = ImmediateCountdown.Factories.createNew()
    val connection = connection(countdownFactory)

    // when
    connection.accept(StartCountdown(3, SECONDS))

    // then
    receivedEvents.assertValues(CountdownTick, CountdownTick, CountdownTick, CountdownComplete)
  }

  @Test
  fun `any existing countdowns should be cancelled when the effect handler is disposed`() {
    // given
    val countdown = DoNothingCountdown()
    val countdownFactory = DoNothingCountdown.Factories.exact(countdown)
    val connection = connection(countdownFactory)

    // when
    connection.accept(StartCountdown(3, SECONDS))
    assertThat(countdown.cancelled).isFalse()

    // then
    connection.dispose()
    assertThat(countdown.cancelled).isTrue()
  }

  private fun connection(countdownFactory: Countdown.Factory): Connection<CounterEffect> {
    val effectHandler =
      CounterEffectHandler(
        countdownFactory = countdownFactory,
        uiWorkRunner = ImmediateUiWorkRunner()
      )

    return effectHandler.connect(receivedEvents)
  }
}
