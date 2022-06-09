package com.belkanoid.notes.model.noteModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey
    val id : UUID = UUID.randomUUID(),
    var content : String = "",
    val date : Date = Date()
)
