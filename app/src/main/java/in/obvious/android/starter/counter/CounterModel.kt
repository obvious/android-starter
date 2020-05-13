package `in`.obvious.android.starter.counter

data class CounterModel(
    val counterValue: Int = 0
) {

    fun increment(): CounterModel {
        return copy(counterValue = counterValue + 1)
    }

    fun decrement(): CounterModel {
        return copy(counterValue = counterValue - 1)
    }
}
