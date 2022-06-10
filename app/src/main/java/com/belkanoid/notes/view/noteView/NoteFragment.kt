package com.belkanoid.notes.view.noteView

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.belkanoid.notes.R
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.model.repository.NoteRepository
import com.belkanoid.notes.utils.Constants
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import java.util.*



class NoteFragment : Fragment(), ColorPickerDialogListener, INoteView{

    interface Callbacks {
        fun onNoteDeleted()
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
                updateUI()
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
                true
            }
            R.id.color_note -> {
                createColorPickerDialog()
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
            override fun afterTextChanged(sequence: Editable?) {
            }
        }

        contentText.addTextChangedListener(titleWatcher)

    }


    override fun onStop() {
        super.onStop()
        onSaveNote(note)
    }


    private fun updateUI() {
        contentText.text = this.note.content
        contentText.setBackgroundColor(this.note.color)
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
        callbacks?.onNoteDeleted()
    }

    private fun createColorPickerDialog() {
        ColorPickerDialog.newBuilder()
            .setColor(Color.RED)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setAllowPresets(true)
            .setColorShape(ColorShape.SQUARE)
            .setDialogId(Constants.COLOR_PICK)
            .show(activity)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        note.color = color
        updateUI()
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

}