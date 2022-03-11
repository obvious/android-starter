package `in`.obvious.android.starter.mobius

interface UiWorkRunner {
  fun post(work: () -> Unit)
}
