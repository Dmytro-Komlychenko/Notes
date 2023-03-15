package com.example.presentation.screens.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteBinding
import com.google.android.material.snackbar.Snackbar


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    private var menuActionSave: MenuItem? = null

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        setHasOptionsMenu(true)

        viewModel.note.observe(viewLifecycleOwner) {
            binding.etTitle.setText(it.title)
            binding.etDescription.setText(it.description)
            setVisibilityButtonSaveHandler(binding.etTitle, viewModel.note.value!!.title)
            setVisibilityButtonSaveHandler(binding.etDescription, viewModel.note.value!!.title)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menuActionSave = menu.findItem(R.id.action_save)
        menuActionSave?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO: save edited note
                Snackbar.make(binding.etTitle, "Note is saved", Snackbar.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setVisibilityButtonSaveHandler(editText: EditText, savedText: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (menuActionSave == null) return
                menuActionSave?.isVisible =
                    binding.etTitle.text.toString() != viewModel.note.value!!.title ||
                            binding.etDescription.text.toString() != viewModel.note.value!!.description
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}