package de.leyn.noteapp

import android.text.Editable

/**
 * Created by Leyn on 16.11.2021.
 */

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)