package com.belkanoid.notes.view.noteListView

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.notes.R
import com.belkanoid.notes.model.noteModel.Note
import com.belkanoid.notes.utils.Constants
import java.util.*

class NoteListFragment : Fragment(), INoteListView {

    interface Callbacks {
        fun onNoteSelected(id : UUID)
    }
    private var callbacks : Callbacks? = null

    private lateinit var recyclerView: RecyclerView

    private val noteListViewModel : NoteListViewModel by lazy {
        ViewModelProvider(this).get(NoteListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_note_list, container, false)

        recyclerView = view.findViewById(R.id.note_recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteListViewModel.noteListLiveData.observe(viewLifecycleOwner, Observer{
            notes -> notes?.let {
            recyclerView.adapter = NoteAdapter(notes)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.new_note -> {
                val note = Note()
                noteListViewModel.addNote(note)
                callbacks?.onNoteSelected(note.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }



    private inner class NoteHolder(view : View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var note : Note

        private val contentText : TextView = itemView.findViewById(R.id.content_note)
        private val dateText : TextView = itemView.findViewById(R.id.date_note)

        fun bind(_note: Note) {
            this.note = _note
            contentText.text = _note.content
            dateText.text = DateFormat.format(Constants.DATE_FORMAT, _note.date).toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            callbacks?.onNoteSelected(note.id)
        }
    }

    private inner class NoteAdapter(private val notes : List<Note>)
        : RecyclerView.Adapter<NoteHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.list_item_note, parent, false)
            return  NoteHolder(view)
        }
        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.bind(note)
        }
        override fun getItemCount() = notes.size
    }


    companion object {
        fun newInstance() : NoteListFragment {
            return  NoteListFragment()
        }
    }



    override fun onSaveNote(note: Note) {

    }

    override fun onDeleteNote(note: Note) {

    }
}