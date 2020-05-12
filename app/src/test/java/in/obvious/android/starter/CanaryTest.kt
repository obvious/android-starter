package `in`.obvious.android.starter

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CanaryTest {

    @Test
    fun `test harness should work`() {
        val expected = 2
        val actual = 1 + 1

        assertThat(actual).isEqualTo(expected)
    }
}
