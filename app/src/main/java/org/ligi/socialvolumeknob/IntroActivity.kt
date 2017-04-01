package org.ligi.socialvolumeknob

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        fab.setOnClickListener {
            val text = identifier_edittext.text

            if (text.isBlank()) {
                AlertDialog.Builder(this@IntroActivity)
                        .setMessage("You need to enter a identifier")
                        .show()
            } else {
                State.identifier=text.toString()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }
}
