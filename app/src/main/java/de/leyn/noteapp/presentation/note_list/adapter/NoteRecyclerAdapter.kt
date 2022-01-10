package de.leyn.noteapp.presentation.note_list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.RecyclerNoteCardBinding
import de.leyn.noteapp.domain.model.Note

/**
 * Created by Leyn on 15.11.2021.
 */
class NoteRecyclerAdapter(
    private var noteList: List<Note>,
    private val onNoteClickListener: NoteViewHolder.OnNoteClickListener
) :
    RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_note_card, parent, false)
        return NoteViewHolder(inflatedView, onNoteClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.bindContent(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class NoteViewHolder(view: View, private val onNoteClickListener: OnNoteClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private var binding: RecyclerNoteCardBinding = RecyclerNoteCardBinding.bind(view)

        init {
            view.setOnClickListener(this)
            binding.deleteButton.setOnClickListener {
                onNoteClickListener.onNoteDeleteClicked(adapterPosition)
            }
        }

        fun bindContent(note: Note) {
            binding.noteCard.setBackgroundColor(Color.parseColor(note.color))
            binding.cardTitle.text = note.title
            binding.cardText.text = note.text
            binding.createdDateText.text = note.createdDate
        }

        override fun onClick(v: View?) {
            onNoteClickListener.onNoteClicked(adapterPosition)
        }

        interface OnNoteClickListener {
            fun onNoteClicked(position: Int)
            fun onNoteDeleteClicked(position: Int)
        }


    }
}