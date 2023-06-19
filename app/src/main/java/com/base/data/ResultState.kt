package com.base.data

// A generic class that contains data and status about loading this data.
sealed class ResultState {

    object Loading : ResultState()
    class Failure(val msg: Throwable) : ResultState()
    class Success<T>(val result: T) : ResultState()
    object Init : ResultState()
}
