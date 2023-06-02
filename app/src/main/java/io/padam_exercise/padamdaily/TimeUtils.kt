package io.padam_exercise.padamdaily

import kotlin.time.Duration

object TimeUtils {

    fun Duration.displayHoursAndMinutes(): String {
        var minutes = this.inWholeMinutes.coerceAtLeast(1)
        var hours = this.inWholeHours
        if (minutes < 60) {
            hours = 0
        } else {
            minutes %= 60
        }

        return if (hours == 0L) {
            "${minutes}min"
        } else if (minutes == 0L) {
            "${hours}h"
        } else {
            "${hours}h ${minutes}min"
        }
    }

}
