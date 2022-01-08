package de.leyn.noteapp.ui.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import de.leyn.noteapp.App
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.ActivitySingleNoteBinding
import de.leyn.noteapp.db.NoteBean
import de.leyn.noteapp.extensions.convertToString
import de.leyn.noteapp.toEditable
import de.leyn.noteapp.ui.viewmodel.NoteViewModel
import de.leyn.noteapp.ui.viewmodel.ViewModelFactory
import java.util.*

class SingleNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleNoteBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            val backArrow = ResourcesCompat.getDrawable(resources, R.drawable.arrow_back, null)
            backArrow?.colorFilter = PorterDuffColorFilter(
                resources.getColor(R.color.onPrimary, null),
                PorterDuff.Mode.SRC_ATOP
            )
            setHomeAsUpIndicator(backArrow)
        }
        val db = (application as App).getDB()
        viewModel = ViewModelFactory(db).create(NoteViewModel::class.java)


        if (intent.hasExtra(App.INTENT_NOTE)) {
            viewModel.isNewNote = false
            val note = intent.getSerializableExtra(App.INTENT_NOTE) as NoteBean
            viewModel.currentNote = note
            binding.titleEditText.text = note.title.toEditable()
            binding.textEditText.text = note.text.toEditable()
            supportActionBar?.title = note.title

        } else {
            viewModel.isNewNote = true
            supportActionBar?.title = resources.getString(R.string.new_note)
        }

        binding.titleEditText.doOnTextChanged { text, _, _, _ ->
            supportActionBar?.title = text
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        if (viewModel.isNewNote) {
            if (!isEmptyContent()) {
                viewModel.insertNote(
                    NoteBean(
                        title = binding.titleEditText.text.toString(),
                        text = binding.textEditText.text.toString(),
                        createdDate = Date().convertToString(),
                        lastEditedDate = Date().convertToString()
                    )
                )
            }

        } else {
            viewModel.currentNote.apply {
                // TODO: lastEditedDate is also changed even if actual Note content has not changed.
                //  Need to compare original message with new message.
                title = binding.titleEditText.text.toString()
                text = binding.textEditText.text.toString()
                lastEditedDate = Date().convertToString()
            }

            viewModel.updateNote()
        }

        onBackPressed()
        return true
    }

    private fun isEmptyContent(): Boolean {
        return binding.textEditText.text.toString()
            .isEmpty() && binding.titleEditText.text.toString().isEmpty()
    }
}