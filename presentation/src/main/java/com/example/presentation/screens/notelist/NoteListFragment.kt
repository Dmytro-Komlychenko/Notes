package com.example.presentation.screens.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.presentation.screens.note.NoteFragment


class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding get() = _binding!!

    private var adapter: NoteItemAdapter? = null

    private lateinit var simpleCallback: SwipeGesture

    private lateinit var itemTouchHelper: ItemTouchHelper

    private val viewModel: NoteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(layoutInflater)

        parentFragmentManager.setFragmentResultListener(NoteFragment.NOTE_FRAGMENT_FINISH, this) { requestKey, result ->
            val value = result.getBoolean(NoteFragment.NOTE_FRAGMENT_FINISH)
            if (value) initAdapter()
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            if (viewModel.notes.value!!.isEmpty()) {
                viewModel.removePosition = null
                startEmptyFragment(binding.recyclerView)
                return@observe
            }

            if (adapter == null) {
                initAdapter()
                return@observe
            }

            if (viewModel.toAdd) {
                adapter!!.addNote(viewModel.notes.value!!.first())
                viewModel.toAdd = false
            }

            if (viewModel.removePosition != null) {
                adapter?.removeFromPosition(viewModel.removePosition!!)
                viewModel.removePosition = null
            }
        }

        return binding.root
    }

    private fun initAdapter() {
        adapter = NoteItemAdapter(viewModel.notes.value!!.toMutableList())
        simpleCallback = SwipeGesture(viewModel)
        itemTouchHelper = ItemTouchHelper(simpleCallback)
        binding.recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun startEmptyFragment(view: View) {
        view.findNavController().navigate(
            R.id.action_noteListFragment_to_emptyFragment
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}