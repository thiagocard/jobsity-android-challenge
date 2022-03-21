package com.jobsity.android.challenge.data.model

import com.jobsity.android.challenge.data.serializer.LocalDateSerializer
import com.jobsity.android.challenge.data.serializer.LocalTimeSerializer
import com.jobsity.android.challenge.data.serializer.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime

@Serializable
data class Episode(
    @SerialName("_links")
    val links: Links,
    @SerialName("airdate")
    @Serializable(with = LocalDateSerializer::class)
    val airdate: LocalDate,
    @SerialName("airstamp")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val airstamp: OffsetDateTime,
    @SerialName("airtime")
    @Serializable(with = LocalTimeSerializer::class)
    val airtime: LocalTime,
    val id: Int,
    val image: Image?,
    val name: String,
    val number: Int,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val summary: String?,
    val type: String,
    val url: String
)