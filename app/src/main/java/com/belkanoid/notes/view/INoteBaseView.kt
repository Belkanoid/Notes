package com.belkanoid.notes.view

import com.belkanoid.notes.model.noteModel.Note

interface INoteBaseView {

    fun onSaveNote(note : Note)
    fun onDeleteNote(note : Note)

}