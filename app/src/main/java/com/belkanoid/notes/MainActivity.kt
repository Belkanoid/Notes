package com.belkanoid.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.belkanoid.notes.view.noteListView.NoteListFragment
import com.belkanoid.notes.view.noteView.NoteFragment
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val noteListFragment = NoteListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, noteListFragment)
                .commit()

        }
    }

    override fun onNoteSelected(id: UUID) {
        val noteFragment = NoteFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, noteFragment)
            .addToBackStack(null)
            .commit()
    }
}