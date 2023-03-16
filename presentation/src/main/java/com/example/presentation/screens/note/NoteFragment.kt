package com.example.presentation.screens.note

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteBinding
import com.example.presentation.app.App
import com.example.presentation.screens.notelist.NoteItemAdapter
import com.example.presentation.utils.CalendarConverter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    private var menuActionSave: MenuItem? = null

    private lateinit var viewModel: NoteViewModel

    @Inject
    lateinit var numberViewModelFactory: NoteViewModelFactory

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)

        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, numberViewModelFactory)[NoteViewModel::class.java]
        viewModel.note.value = arguments?.parcelable(NoteItemAdapter.NOTE_KEY)

        setHasOptionsMenu(true)

        viewModel.note.observe(viewLifecycleOwner) {
            if (viewModel.wasFragmentShown) return@observe
            viewModel.wasFragmentShown = true

            binding.etTitle.setText(it.title)
            binding.etDescription.setText(it.description)
            setVisibilityButtonSaveHandler(binding.etTitle)
            setVisibilityButtonSaveHandler(binding.etDescription)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menuActionSave = menu.findItem(R.id.action_save)
        setSaveButtonVisibility()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO: save edited note
                Snackbar.make(binding.etTitle, "Note is saved", Snackbar.LENGTH_SHORT).show()
                viewModel.note.value?.apply {
                    title = binding.etTitle.text.toString()
                    description = binding.etDescription.text.toString()
                    dateOfChange = CalendarConverter.convertToString(CalendarConverter.getCurrentCalendar())
                }
                viewModel.saveNote()
                menuActionSave?.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setVisibilityButtonSaveHandler(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                text: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (menuActionSave == null) return
                setSaveButtonVisibility()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setSaveButtonVisibility() {
        menuActionSave?.isVisible =
            binding.etTitle.text.toString() != viewModel.note.value!!.title ||
                    binding.etDescription.text.toString() != viewModel.note.value!!.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        setFragmentResult(NOTE_FRAGMENT_FINISH, bundleOf(NOTE_FRAGMENT_FINISH to true))
    }

    companion object {
        const val NOTE_FRAGMENT_FINISH = "NOTE_FRAGMENT_FINISH"
    }
}