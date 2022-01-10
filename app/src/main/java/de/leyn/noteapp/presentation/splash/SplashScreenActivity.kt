package de.leyn.noteapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import de.leyn.noteapp.R
import de.leyn.noteapp.presentation.note_list.OverviewListActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val ivLogo: ImageView = findViewById(R.id.ivLogo)
        val anim: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.grow_size_from_0_to_100)

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                goToNextActivity()
            }
        })

        ivLogo.startAnimation(anim)
    }

    private fun goToNextActivity() {
        val intent = Intent(this, OverviewListActivity::class.java)
        startActivity(intent)
    }
}