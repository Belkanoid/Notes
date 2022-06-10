package com.belkanoid.notes.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.belkanoid.notes.model.noteModel.Note


@Database(entities = [Note::class], version = 3)
@TypeConverters(NoteConvertorType::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao


//    object migration_2_3  : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE Note MODIFY COLUMN color BIGINT")
//        }
//    }
}