package de.leyn.noteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.leyn.noteapp.extensions.convertToString
import java.io.Serializable
import java.text.DateFormat
import java.text.DateFormat.DEFAULT
import java.text.DateFormat.getDateTimeInstance
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Leyn on 14.11.2021.
 */
@Entity(tableName = "notes")
data class NoteBean(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "createdDate") var createdDate: String,
    @ColumnInfo(name = "lastEditedDate") var lastEditedDate: String
) : Serializable {
}