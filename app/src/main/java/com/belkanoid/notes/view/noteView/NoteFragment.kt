package com.belkanoid.notes.view.noteView

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belkanoid.notes.R
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.utils.Constants
import com.belkanoid.notes.view.noteListView.NoteListFragment
import com.belkanoid.notes.view.noteListView.NoteListViewModel
import java.util.*


class NoteFragment : Fragment(), INoteView{

    interface Callbacks {
        fun onNoteDeleted(fragment: NoteFragment)
    }

    private var callbacks: Callbacks? = null
    private lateinit var note : Note
    private val noteViewModel : NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    private lateinit var contentText : TextView


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        contentText = view.findViewById(R.id.content_note)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel.noteLiveData.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                this.note = note
                contentText.text = this.note.content
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()
        val noteId = arguments?.getSerializable(Constants.ARG_NOTE_ID) as UUID
        noteViewModel.loadNote(noteId)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete_note -> {
                onDeleteNote(note)
                callbacks?.onNoteDeleted(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                note.content = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {}
        }

        contentText.addTextChangedListener(titleWatcher)

    }

    override fun onStop() {
        super.onStop()
        onSaveNote(note)
    }



    companion object {
        fun newInstance(id : UUID) : NoteFragment {
            val args = Bundle().apply {
                putSerializable(Constants.ARG_NOTE_ID, id)
            }
            return NoteFragment().apply {
                arguments = args
            }
        }
    }


    override fun onSaveNote(note: Note) {
        noteViewModel.saveNote(note)
    }

    override fun onDeleteNote(note: Note) {
        noteViewModel.deleteNote(note)
    }
}