package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INotesItemClicked{

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val mAdapter = NotesRVAdapter(this, this)
        binding.recyclerView.adapter = mAdapter


        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                mAdapter.updateList(it)
            }
        })

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()

    }

    fun submitData(view: View) {
        val noteText = binding.editText.text.toString()
        if(noteText.isNotEmpty()) {
            viewModel.insertNode(Note(noteText))
            Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_SHORT).show()
        }
    }
}