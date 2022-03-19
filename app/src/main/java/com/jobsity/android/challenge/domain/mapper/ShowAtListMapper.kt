package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowAtList

object ShowAtListMapper : Mapper<Show, ShowAtList> {

    override fun map(input: Show) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.image?.medium ?: ""
    )

}
