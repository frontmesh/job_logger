package com.frontmatic.sendwrite.ext

fun String.cap() = this.lowercase().replaceFirstChar { it.uppercase() }