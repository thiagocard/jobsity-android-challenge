package com.jobsity.android.challenge.domain.mapper

interface Mapper<I, O> {

    fun map(input: I): O

}
