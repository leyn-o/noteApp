package de.leyn.noteapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import de.leyn.noteapp.Note
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.AddNoteBinding

/**
 * Created by Leyn on 14.11.2021.
 */
class AddNoteDialog : DialogFragment() {

    internal lateinit var listener: AddNoteDialogListener
    lateinit var addNoteView: View
    lateinit var binding: AddNoteBinding

    interface AddNoteDialogListener {
        fun onDialogPositiveClick(newNote: Note)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddNoteDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement AddNoteDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = requireActivity().layoutInflater
        addNoteView = layoutInflater.inflate(R.layout.add_note, null)

        binding = AddNoteBinding.inflate(layoutInflater)
        val inputTitle: TextInputEditText = binding.inputTitle
        val inputText: TextInputEditText = binding.inputText


        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.apply {
            setView(addNoteView)
            setTitle(R.string.new_note)
            setPositiveButton(R.string.save) { _, _ ->
                val title = inputTitle.text.toString()
                val text = inputText.text.toString()
                listener.onDialogPositiveClick(Note(title, text))
            }
            setNegativeButton(R.string.cancel) { _, _ ->
                dismiss()
            }
        }.create()


        return dialog
    }

}