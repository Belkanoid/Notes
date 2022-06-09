package com.belkanoid.notes.view.noteListView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.model.repository.NoteRepository

class NoteListViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()
    val noteListLiveData: LiveData<List<Note>> = noteRepository.getNotes()
    fun addNote(note : Note) {
        noteRepository.addNote(note)
    }

//    val noteListLiveData = mutableListOf<Note>()
//    init {
//        for (i in 0 until 10) {
//            val note = Note(content = "$i")
//            noteListLiveData.add(note)
//        }
//    }

}