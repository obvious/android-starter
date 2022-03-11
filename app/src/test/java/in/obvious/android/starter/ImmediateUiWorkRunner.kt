package `in`.obvious.android.starter

import `in`.obvious.android.starter.mobius.UiWorkRunner

class ImmediateUiWorkRunner : UiWorkRunner {

  override fun post(work: () -> Unit) {
    work()
  }
}
