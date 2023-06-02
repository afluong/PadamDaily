package io.padam_exercise.padamdaily

import io.padam_exercise.padamdaily.TimeUtils.displayHoursAndMinutes
import org.junit.Assert
import org.junit.Test
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class TimeUtilsTest {
    @Test
    fun displayHoursAndMinutesIsCorrect() {
        Assert.assertTrue(
            "Test 1",
            34L.seconds.displayHoursAndMinutes() == "1min"
        )

        Assert.assertTrue(
            "Test 2",
            67L.seconds.displayHoursAndMinutes() == "1min"
        )

        Assert.assertTrue(
            "Test 3",
            59L.minutes.displayHoursAndMinutes() == "59min"
        )

        Assert.assertTrue(
            "Test 4",
            60L.minutes.displayHoursAndMinutes() == "1h"
        )

        Assert.assertTrue(
            "Test 5",
            61L.minutes.displayHoursAndMinutes() == "1h 1min"
        )

        Assert.assertTrue(
            "Test 6",
            120L.minutes.displayHoursAndMinutes() == "2h"
        )

        Assert.assertTrue(
            "Test 7",
            0L.minutes.displayHoursAndMinutes() == "1min"
        )

        Assert.assertTrue(
            "Test 8",
            74L.minutes.displayHoursAndMinutes() == "1h 14min"
        )
    }
}