package org.ligi.socialvolumeknob

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (State.identifier != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        finish()

    }
}
