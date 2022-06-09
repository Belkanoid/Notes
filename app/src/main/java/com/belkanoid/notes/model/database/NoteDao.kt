package com.belkanoid.notes.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.belkanoid.notes.model.noteModel.Note
import java.util.*


@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id=(:id)")
    fun getNote(id : UUID) : LiveData<Note?>

    @Insert
    fun addNote(note : Note)

    @Update
    fun updateNote(note : Note)

    @Delete
    fun deleteNote(note: Note)
}