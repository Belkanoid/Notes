package com.belkanoid.notes.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.belkanoid.notes.model.noteModel.Note


@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteConvertorType::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao
}