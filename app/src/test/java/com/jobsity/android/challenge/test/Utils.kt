package com.jobsity.android.challenge.test

import com.jobsity.android.challenge.domain.model.ShowDetails
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

val sampleShowDetails = ShowDetails(
    id = 1,
    name = "Game of Thrones",
    poster = "http://url.to/some/poster.png",
    airsAt = "Airs at someday",
    genres = listOf("Drama", "Suspense", "Fiction"),
    summary = "Bla bla bla...",
    rating = 9.5,
    status = "Ended",
    year = 2022,
)

/**
 * Read json file from path and help to build defined structures.
 */
fun readJson(context: Any, path: String) =
    File(context.javaClass.classLoader?.getResource(path)!!.file).readText()

inline fun <reified T> fromJson(context: Any, path: String) =
    json.decodeFromString<T>(readJson(context, path))