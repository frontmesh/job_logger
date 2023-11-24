package com.frontmatic.sendwrite.local.mapper

import com.frontmatic.sendwrite.local.model.Job
import com.kedia.ogparser.OpenGraphResult
import java.util.Date

fun OpenGraphResult.toJob(
//    applied: Boolean
): Job {
    return Job(
        title = title!!,
        description = description!!,
        url = url!!,
        imageUrl = image!!,
//        applied = if (applied) Date() else null,
        archived = false,
        createdAt = System.currentTimeMillis(),
        modifiedAt = System.currentTimeMillis()
    )
}