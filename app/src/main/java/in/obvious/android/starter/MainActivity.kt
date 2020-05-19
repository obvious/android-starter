package `in`.obvious.android.starter

import `in`.obvious.android.starter.counter.CounterScreen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if(savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .add(R.id.container, CounterScreen())
        .commit()
    }
  }
}
