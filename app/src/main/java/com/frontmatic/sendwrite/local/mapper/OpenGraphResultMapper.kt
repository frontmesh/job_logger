package com.frontmatic.sendwrite.local.mapper

import com.frontmatic.sendwrite.local.model.Job
import com.kedia.ogparser.OpenGraphResult

fun OpenGraphResult.toJob(applied: Boolean): Job {
    return Job(
        title = title!!,
        description = description!!,
        url = url!!,
        imageUrl = image!!,
        applied = applied,
    )
}