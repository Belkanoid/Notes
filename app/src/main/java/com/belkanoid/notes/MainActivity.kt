package com.belkanoid.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.model.repository.NoteRepository
import com.belkanoid.notes.utils.NoteApplication
import com.belkanoid.notes.view.noteListView.NoteListFragment
import com.belkanoid.notes.view.noteView.NoteFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.Callbacks, NoteFragment.Callbacks,
    ColorPickerDialogListener {

    private val noteApplication = NoteApplication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onCreateNoteList(savedInstanceState)

    }

    override fun onNoteSelected(id: UUID) {
        val noteFragment = NoteFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, noteFragment)
            .addToBackStack(null)
            .commit()
    }
    private fun onCreateNoteList(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val noteListFragment = NoteListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, noteListFragment)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        onCreateNoteList(outState)
    }

    override fun onNoteDeleted() {
        supportFragmentManager.popBackStack()
    }

    override fun onColorSelected(dialogId: Int, @ColorInt color: Int) {
        val fragment : Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is ColorPickerDialogListener) {
            (fragment as ColorPickerDialogListener?)!!.onColorSelected(dialogId, color)
        }
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

}