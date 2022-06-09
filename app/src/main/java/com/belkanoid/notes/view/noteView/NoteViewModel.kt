package com.belkanoid.notes.view.noteView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.model.repository.NoteRepository
import java.util.*

class NoteViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()
    private val noteIdLiveData = MutableLiveData<UUID>()

    val noteLiveData : LiveData<Note?> =
        Transformations.switchMap(noteIdLiveData) { noteId ->
            noteRepository.getNote(noteId)
    }

    fun loadNote(noteId : UUID) {
        noteIdLiveData.value = noteId
    }

    fun saveNote(note : Note) {
        noteRepository.updateNote(note)
    }
}