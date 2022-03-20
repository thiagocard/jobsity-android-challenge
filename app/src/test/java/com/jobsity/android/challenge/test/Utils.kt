package com.jobsity.android.challenge.test

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

/**
 * Read json file from path and help to build defined structures.
 */
fun readJson(context: Any, path: String) =
    File(context.javaClass.classLoader?.getResource(path)!!.file).readText()

inline fun <reified T>fromJson(context: Any, path: String) = json.decodeFromString<T>(readJson(context, path))