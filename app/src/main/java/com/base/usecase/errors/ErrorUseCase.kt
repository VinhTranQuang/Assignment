package com.base.usecase.errors

import com.base.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
