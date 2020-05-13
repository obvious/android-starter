package `in`.obvious.android.starter.counter

sealed class CounterEvent

object IncrementClicked: CounterEvent()

object DecrementClicked: CounterEvent()

object StartCountdownClicked: CounterEvent()

object CountdownTick: CounterEvent()
