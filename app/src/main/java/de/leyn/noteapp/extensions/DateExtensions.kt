package de.leyn.noteapp.extensions

import java.text.SimpleDateFormat
import java.util.*


const val dateTimePattern = "dd.MM.yy HH:mm:ss"
private val locale = Locale("de", "DE")
private val dateFormatter = SimpleDateFormat(dateTimePattern, locale)

fun String.convertToDate(): Date? = dateFormatter.parse(this)

fun Date.convertToString(): String = dateFormatter.format(this)