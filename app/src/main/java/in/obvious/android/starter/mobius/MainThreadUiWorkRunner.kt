package `in`.obvious.android.starter.mobius

import android.os.Handler
import android.os.Looper

class MainThreadUiWorkRunner : UiWorkRunner {

    private val handler = Handler(Looper.getMainLooper())

    override fun post(work: () -> Unit) {
        handler.post(work)
    }
}
