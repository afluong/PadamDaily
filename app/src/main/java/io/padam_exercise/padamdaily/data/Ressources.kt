package io.padam_exercise.padamdaily.data

import io.padam_exercise.padamdaily.utils.Toolbox

data class Resource<out T>(val status: Toolbox.Companion.Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Toolbox.Companion.Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Toolbox.Companion.Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Toolbox.Companion.Status.LOADING, data = data, message = null)
    }
}
