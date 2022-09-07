package com.jobsity.android.challenge.ext

fun <T> Result<T>.exception(): Throwable {
    return this.exceptionOrNull()!!
}