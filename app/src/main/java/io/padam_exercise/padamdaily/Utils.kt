package io.padam_exercise.padamdaily

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlin.time.Duration

object Utils {

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

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    fun getGoogleMapsUrl(origin: LatLng, destination: LatLng): String {
        return "http://maps.google.com/maps?saddr=${origin.latitude},${origin.longitude} &daddr=${destination.latitude},${destination.longitude} &travelmode=1"
    }

}
