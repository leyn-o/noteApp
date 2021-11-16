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
import de.leyn.noteapp.toEditable
import de.leyn.noteapp.ui.viewmodel.SingleNoteViewModel
import de.leyn.noteapp.ui.viewmodel.ViewModelFactory

class SingleNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleNoteBinding
    private lateinit var viewModel: SingleNoteViewModel

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
        viewModel = ViewModelFactory(db).create(SingleNoteViewModel::class.java)


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
            if(!isEmptyContent()) {
                viewModel.insertNote(
                    NoteBean(
                        binding.titleEditText.text.toString(),
                        binding.textEditText.text.toString()
                    )
                )
            }

        } else {
            viewModel.currentNote.apply {
                title = binding.titleEditText.text.toString()
                text = binding.textEditText.text.toString()
            }

            viewModel.updateNote()
        }

        onBackPressed()
        return true
    }

    private fun isEmptyContent(): Boolean {
        return binding.textEditText.text.toString().isEmpty() && binding.titleEditText.text.toString().isEmpty()
    }
}