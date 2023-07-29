package com.example.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRVAdapter(private val context: Context, val listener: INotesRVAdapter): RecyclerView.Adapter<NoteViewHolder>() {

    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onDeleteClicked(allNotes[viewHolder.adapterPosition])
        }
        viewHolder.editButton.setOnClickListener {
            listener.onEditClicked(allNotes[viewHolder.adapterPosition])
        }
        viewHolder.noteText.setOnClickListener {
            listener.onNoteClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.noteText.text = currentNote.text
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val noteText = itemView.findViewById<TextView>(R.id.text)
    val deleteButton = itemView.findViewById<ImageView>(R.id.delete)
    val editButton = itemView.findViewById<ImageView>(R.id.edit)

}

interface INotesRVAdapter{
    fun onDeleteClicked(note: Note)
    fun onEditClicked(note: Note)
    fun onNoteClicked(note: Note)
}