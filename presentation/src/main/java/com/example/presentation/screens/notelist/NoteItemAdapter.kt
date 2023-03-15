package com.example.presentation.screens.notelist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.example.notes.databinding.FragmentItemBinding
import com.example.presentation.models.Note

class NoteItemAdapter(
    private val notes: MutableList<Note>
) : RecyclerView.Adapter<NoteItemAdapter.ViewHolder>() {

    private lateinit var holder: ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        holder = ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    fun addNote(note: Note) {
        val diffUtil = MyDiffUtil(notes, notes + note)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        notes.add(0, note)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeFromPosition(position: Int) {
        val note = notes[position]
        val diffUtil = MyDiffUtil(notes, notes - note)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        notes.remove(note)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(private val binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.description
            tvChangeDate.text = note.dateOfChange
        }
    }
}


class MyDiffUtil(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }
}
