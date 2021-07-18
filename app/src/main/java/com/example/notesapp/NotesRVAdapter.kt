package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNotesBinding

class NotesRVAdapter(val context: Context, private val listener: INotesItemClicked) :
    RecyclerView.Adapter<NotesRVAdapter.NotesViewHolder>() {

    private val allNotes: ArrayList<Note> = ArrayList()

    class NotesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = NotesViewHolder(view)

        view.ivDeleteButton.setOnClickListener {
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.binding.textView.text = currentNote.text
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }

}

interface INotesItemClicked {
    fun onItemClicked(item: Note)
}

