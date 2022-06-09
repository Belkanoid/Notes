package com.belkanoid.notes.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.belkanoid.notes.model.database.NoteDatabase
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.utils.Constants
import java.util.*
import java.util.concurrent.Executors

class NoteRepository private constructor(context: Context){

    private val database : NoteDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes() : LiveData<List<Note>> = noteDao.getNotes()

    fun getNote(id : UUID) : LiveData<Note?> = noteDao.getNote(id)

    fun addNote(note : Note) {
        executor.execute {
            noteDao.addNote(note)
        }
    }

    fun updateNote(note : Note) {
        executor.execute{
            noteDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        executor.execute{
            noteDao.deleteNote(note)
        }
    }

    companion object {
        private var INSTANCE : NoteRepository? = null
        fun initialize(context : Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }
        fun get() : NoteRepository {
            return INSTANCE ?:
            throw
            IllegalStateException("NoteRepository must me initialized")
        }
    }

}