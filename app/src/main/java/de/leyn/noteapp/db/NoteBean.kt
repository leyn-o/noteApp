package de.leyn.noteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Leyn on 14.11.2021.
 */
@Entity(tableName = "notes")
data class NoteBean(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "text") val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}