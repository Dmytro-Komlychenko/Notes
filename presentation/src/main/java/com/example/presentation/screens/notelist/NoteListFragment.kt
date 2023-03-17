package com.example.presentation.screens.notelist

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.presentation.models.Note
import com.example.presentation.screens.note.NoteFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding get() = _binding!!

    private var adapter: NoteItemAdapter? = null

    private lateinit var simpleCallback: SwipeGesture

    private lateinit var itemTouchHelper: ItemTouchHelper

    private val viewModel: NoteListViewModel by activityViewModels()

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(layoutInflater)

        viewModel.notes.observe(viewLifecycleOwner) {

            binding.pbLoading.isVisible = false


            var job: Job? = null
            binding.pbProgress.isVisible = false

            viewModel.isInternetConnection.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbProgress.isVisible = true
                    job = lifecycleScope.launch {
                        binding.pbProgress.progress = 0
                        for (i in 0 until 5000) {
                            binding.pbProgress.progress += 1
                            delay(1)
                        }
                        binding.pbProgress.isVisible = false
                    }
                } else {
                    binding.pbProgress.isVisible = false
                    job?.cancel()
                    job = null
                }


            }

            binding.recyclerView.isVisible = true

            if (viewModel.notes.value!!.isEmpty()) return@observe

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


        parentFragmentManager.setFragmentResultListener(
            NoteFragment.NOTE_FRAGMENT_FINISH,
            this
        ) { requestKey, result ->
            val value = result.parcelable<Note>(NoteFragment.NOTE_FRAGMENT_FINISH)
            viewModel.notes.value?.find { note -> note.id == value?.id }?.apply {
                title = value?.title.toString()
                description = value?.description.toString()
            }

            if (value != null) {
                adapter?.updateNoteValue(value)
                binding.recyclerView.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}