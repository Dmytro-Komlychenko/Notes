package com.example.presentation.screens.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.presentation.models.Note
import com.example.presentation.utils.CalendarConverter
import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter
import java.util.*

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding get() = _binding!!

    private lateinit var adapter: NoteItemAdapter

    private val simpleCallback: SwipeGesture by lazy {
        SwipeGesture(adapter)
    }

    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(simpleCallback)
    }

    var notes = mutableListOf<Note>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(layoutInflater)

        setCreateNoteButtonHandler()

        val dates = listOf(
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.YEAR, 2023)
            },

            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 2)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.YEAR, 2023)
            },

            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 3)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.YEAR, 2023)
            },

            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 4)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.YEAR, 2023)
            },

            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 5)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.YEAR, 2023)
            },

            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 15)
                set(Calendar.MONTH, Calendar.MARCH)
                set(Calendar.YEAR, 2023)
                set(Calendar.HOUR_OF_DAY , 10)
                set(Calendar.MINUTE, 42)
            },
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 15)
                set(Calendar.MONTH, Calendar.MARCH)
                set(Calendar.YEAR, 2023)
                set(Calendar.HOUR_OF_DAY , 22)
                set(Calendar.MINUTE, 14)
            }
        )

        notes = mutableListOf(
            Note(0, "Title 1", "Description 1", CalendarConverter.convertToShow(dates[0])),
            Note(1, "Title 2", "Description 2", CalendarConverter.convertToShow(dates[1])),
            Note(2, "Title 3", "Description 3", CalendarConverter.convertToShow(dates[2])),
            Note(3, "Title 4", "Description 4", CalendarConverter.convertToShow(dates[3])),
            Note(4, "Title 5", "Description 5", CalendarConverter.convertToShow(dates[4])),
            Note(5, "Title 6", "Description 6", CalendarConverter.convertToShow(dates[5])),
            Note(6, "Title 7", "Description 7", CalendarConverter.convertToShow(dates[6])),
        )



        adapter = NoteItemAdapter(notes)
        binding.recyclerView.adapter = adapter


        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(notes.isEmpty()) {
            view.findNavController().navigate(
                R.id.action_noteListFragment_to_emptyFragment
            )
        }
    }

    private fun setCreateNoteButtonHandler() {
        binding.fabCreateNote.setOnClickListener {
            Snackbar.make(binding.root, "Note is created", Snackbar.LENGTH_SHORT).show()
            val newNote =
                Note(
                    5, // TODO: get last id
                    "Новая заметка",
                    "",
                    CalendarConverter.convertToShow(CalendarConverter.getCurrentCalendar())
                )
            adapter.addNote(newNote)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}