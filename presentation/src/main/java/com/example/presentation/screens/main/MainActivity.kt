package com.example.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.presentation.app.App
import com.example.presentation.models.Note
import com.example.presentation.screens.notelist.NoteListViewModel
import com.example.presentation.screens.notelist.NoteListViewModelFactory
import com.example.presentation.utils.CalendarConverter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!


    private lateinit var viewModel: NoteListViewModel

    @Inject
    lateinit var numberViewModelFactory: NoteListViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, numberViewModelFactory)[NoteListViewModel::class.java]

        setContentView(binding.root)
        setCreateNoteButtonHandler()
    }

    private fun setCreateNoteButtonHandler() {
        binding.fabCreateNote.setOnClickListener {
            Snackbar.make(binding.root, "Note is created", Snackbar.LENGTH_SHORT).show()

            if (viewModel.notes.value?.isEmpty() == true) {
                findNavController(binding.fragmentContainerView.id).navigate(
                    R.id.action_emptyFragment_to_noteListFragment
                )
            }

            val newId =
                if (viewModel.notes.value?.isEmpty() == true) 0
                else viewModel.notes.value?.first()!!.id.plus(1)

            val newNote =
                Note(
                    newId,
                    "Новая заметка",
                    "",
                    CalendarConverter.convertToString(CalendarConverter.getCurrentCalendar())
                )
            viewModel.addNote(newNote)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}