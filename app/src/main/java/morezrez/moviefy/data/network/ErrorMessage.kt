package morezrez.moviefy.data.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException

private val gson by lazy { Gson() }
private const val MESSAGE = "مشکلی پیش آمده! دوباره تلاش کنید"
private const val DISCONNECT_MESSAGE = "از اتصال دستگاه خود به اینترنت اطمینان حاصل فرمایید"
fun fromNetworkThrowable(throwable: Throwable?): ErrorResponse {
    return if (throwable != null && throwable is HttpException) {
        val networkError = throwable.response()

        try {
            gson.fromJson(
                networkError?.errorBody()?.string().toString(), ErrorResponse::class.java
            ).apply {
                return this.copy()
            }
        } catch (e: Exception) {
            ErrorResponse(throwable.message ?: MESSAGE)
        }
    } else {
        Log.e("networkThrowable", throwable?.message ?: "")
        Log.e("networkThrowable", throwable?.message ?: "")
        ErrorResponse( DISCONNECT_MESSAGE)
    }
}

fun parseErrorBody(error: String): ErrorResponse {
    return try {
        gson.fromJson(error, ErrorResponse::class.java)
    } catch (e: Exception) {
        ErrorResponse( MESSAGE)
    }
}