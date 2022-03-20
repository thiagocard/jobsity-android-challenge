package com.jobsity.android.challenge.domain.usecase

interface UseCase<I, O> {

    suspend operator fun invoke(input: I): Result<O>

}