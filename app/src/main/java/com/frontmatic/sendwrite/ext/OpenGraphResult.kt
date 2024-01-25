package com.frontmatic.sendwrite.ext

import com.frontmatic.sendwrite.local.model.Job
import com.kedia.ogparser.OpenGraphResult

fun OpenGraphResult.convertStringNullsToNull(): OpenGraphResult {
    return OpenGraphResult(
        title = if (title == "null") null else title,
        description = if (description == "null") null else description,
        url = if (url == "null") null else url,
        image = if (image == "null") null else image,
        siteName = if (siteName == "null") null else siteName,
        type = if (type == "null") null else type
    )
}


fun OpenGraphResult.toJob(
//    applied: Boolean
): Job {
    return Job(
        title = title!!,
        description = description,
        url = url!!,
        imageUrl = image,
//        applied = if (applied) Date() else null,
        archived = false,
        createdAt = System.currentTimeMillis(),
        modifiedAt = System.currentTimeMillis()
    )
}