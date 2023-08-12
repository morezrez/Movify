package morezrez.moviefy.data.network

sealed class Resource<out T : Any>{
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val error: ErrorResponse = ErrorResponse("")) : Resource<Nothing>()

}
