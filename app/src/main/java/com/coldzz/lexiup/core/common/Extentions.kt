package com.coldzz.lexiup.core.common

import java.io.IOException

fun Throwable.isNetworkError(): Boolean {
    return this is IOException
}