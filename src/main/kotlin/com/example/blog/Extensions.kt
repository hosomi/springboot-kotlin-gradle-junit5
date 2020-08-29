package com.example.blog

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

fun LocalDateTime.format() = this.format(japaneseDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

private val japaneseDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("yyyy年 M月 d日 HH:mm:ss")
		.toFormatter(Locale.JAPANESE)

private fun getOrdinal(n: Int) = when {
	n in 11..13 -> "${n}th"
	n % 10 == 1 -> "${n}st"
	n % 10 == 2 -> "${n}nd"
	n % 10 == 3 -> "${n}rd"
	else -> "${n}th"
}

fun String.toSlug() = toLowerCase()
		.replace("\n", " ")
		.replace("[^a-z\\d\\s]".toRegex(), " ")
		.split(" ")
		.joinToString("-")
		.replace("-+".toRegex(), "-")
