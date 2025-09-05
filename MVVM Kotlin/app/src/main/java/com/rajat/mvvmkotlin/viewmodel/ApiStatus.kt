package com.rajat.mvvmkotlin.viewmodel

enum class ApiStatus {
  NotHitOnce,IsBeingHit,ApiHitWithError, ApiHit
}
sealed class SealedClass<out T>{
  object NoHit :SealedClass<Nothing>()
  object Loading :SealedClass<Nothing>()
  data class Success<T>(val data: T): SealedClass<T>()
  data class Error(val message: String): SealedClass<Nothing>()
  data class ErrorInternet(val message: String): SealedClass<Nothing>()

}