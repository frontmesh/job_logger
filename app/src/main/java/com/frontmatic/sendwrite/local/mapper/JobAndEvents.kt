package com.frontmatic.sendwrite.local.mapper

import com.frontmatic.sendwrite.local.model.Job
import com.frontmatic.sendwrite.local.model.JobAndEvents

fun JobAndEvents.toJob(): Job {
    return Job(
        id = id,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        archived = archived,
        createdAt = System.currentTimeMillis(),
        modifiedAt = System.currentTimeMillis()
    )
}