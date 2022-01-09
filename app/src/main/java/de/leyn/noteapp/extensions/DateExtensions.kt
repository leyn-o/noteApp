package de.leyn.noteapp.extensions

import java.text.SimpleDateFormat
import java.util.*


private val locale = Locale("de", "DE")
const val dateTimePattern = "dd.MM.yy HH:mm:ss"
private val dateTimeFormatter = SimpleDateFormat(dateTimePattern, locale)

fun String.convertToDate(): Date? = dateTimeFormatter.parse(this)

fun Date.convertToDateTimeString(): String = dateTimeFormatter.format(this)
