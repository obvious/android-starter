package `in`.obvious.android.starter.counter

data class CounterModel(val counterValue: Int = 0, val isCountdownOngoing: Boolean = false) {

  fun increment(): CounterModel {
    return copy(counterValue = counterValue + 1)
  }

  fun decrement(): CounterModel {
    return copy(counterValue = counterValue - 1)
  }

  fun countdownStarted(): CounterModel {
    return copy(isCountdownOngoing = true)
  }

  fun countdownEnded(): CounterModel {
    return copy(isCountdownOngoing = false)
  }
}
