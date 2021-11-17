package de.leyn.noteapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import de.leyn.noteapp.R

/**
 * Created by Leyn on 17.11.2021.
 */
class DeleteConfirmationDialog(
    private val notePosition: Int,
    noteTitle: String
) : DialogFragment() {

    private lateinit var listener: DeleteNoteDialogListener
    val noteTitleQuotationMarks = "\"$noteTitle\""

    interface DeleteNoteDialogListener {
        fun onDialogPositiveClick(position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DeleteNoteDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement AddNoteDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        return builder.apply {
            setTitle(getString(R.string.delete_note))
            setMessage(getString(R.string.delete_note_question, noteTitleQuotationMarks))
            setPositiveButton(R.string.delete) { _, _ ->
                listener.onDialogPositiveClick(notePosition)
            }
            setNegativeButton(R.string.cancel) { _, _ ->
                dismiss()
            }
        }.create()
    }
}