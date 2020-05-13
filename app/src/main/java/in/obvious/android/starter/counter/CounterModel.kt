package `in`.obvious.android.starter.counter

data class CounterModel(
    val counterValue: Int = 0
) {

    fun increment(): CounterModel {
        return copy(counterValue = counterValue + 1)
    }
}
