package com.belkanoid.notes.utils

import android.app.Application
import com.belkanoid.notes.model.repository.NoteRepository

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(context = this)
    }
}