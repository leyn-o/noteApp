package de.leyn.noteapp.ui.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import de.leyn.noteapp.R
import de.leyn.noteapp.databinding.ActivitySingleNoteBinding

class SingleNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleNoteBinding

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
    }

    override fun onSupportNavigateUp(): Boolean {
        // save data if something has changed
        onBackPressed()
        return true
    }
}