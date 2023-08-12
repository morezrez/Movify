package morezrez.moviefy.data.network

import android.util.Log
import kotlin.concurrent.thread

suspend inline fun <T : Any> safeApiCall(
    crossinline call: suspend () -> T
): Resource<T> = runCatching {
    val data = call()
    Log.d("BZRTAG-5","data=$data")
    Resource.Success(data)
}.getOrElse {
    Log.d("BZRTAG-4","getOrElse error=$it")
    Resource.Error(fromNetworkThrowable(it))
}

