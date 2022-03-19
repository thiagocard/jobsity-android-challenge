package com.jobsity.android.challenge.data.model

import com.jobsity.android.challenge.data.serializer.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDate

@Serializable
data class Show(
    @SerialName("_links")
    val links: Links,
    val averageRuntime: Int?,
    @Serializable(with = LocalDateSerializer::class)
    val ended: LocalDate?,
    val externals: Externals,
    val genres: List<String>,
    val id: Int,
    val image: Image?,
    val language: String,
    val name: String,
    val network: Network?,
    val officialSite: String?,
    @Serializable(with = LocalDateSerializer::class)
    val premiered: LocalDate?,
    val rating: Rating,
    val runtime: Int?,
    val schedule: Schedule,
    val status: String,
    val summary: String?,
    val type: String,
    val updated: Int?,
    val url: String,
    val webChannel: WebChannel?,
    val weight: Int
)