package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var mAdapter : NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val noteContent : EditText = findViewById(R.id.noteContent)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NotesRVAdapter(this,this)
        recyclerView.adapter = mAdapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this) { list ->
            list?.let {
                mAdapter.updateList(it)
            }

        }

        val submit: Button = findViewById(R.id.submit)
        submit.setOnClickListener{
            if (noteContent.text.isNotEmpty()){
                viewModel.insertNote(Note(noteContent.text.toString()))
            Toast.makeText(this,"Note Inserted",Toast.LENGTH_SHORT).show()
            }
            noteContent.text.clear()
        }

    }

    override fun onDeleteClicked(note: Note) {

        viewModel.deleteNote(note)
        Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show()

    }

    override fun onEditClicked(note: Note) {
        Toast.makeText(this,"Edit your note and click SAVE NOTE",Toast.LENGTH_LONG).show()
        val noteContent : EditText = findViewById(R.id.noteContent)
        noteContent.text.clear()
        noteContent.setText(note.text)
        viewModel.deleteNote(note)
    }

    override fun onNoteClicked(note: Note) {
        val noteContent : EditText = findViewById(R.id.noteContent)
        noteContent.text.clear()
        noteContent.setText(note.text)
    }

}