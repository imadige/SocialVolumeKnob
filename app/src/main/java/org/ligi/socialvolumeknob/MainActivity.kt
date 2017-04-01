package org.ligi.socialvolumeknob

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        croller.label = State.identifier
        croller.labelSize = resources.getDimension(R.dimen.croller_label_size).toInt()
        croller.setOnProgressChangedListener {

            val relevantStream = AudioManager.STREAM_MUSIC
            val maxVolume = audioManager.getStreamMaxVolume(relevantStream)
            val newVolume = (maxVolume * (it.toDouble() / croller.max)).toInt()

            audioManager.setStreamVolume(relevantStream, newVolume, AudioManager.FLAG_SHOW_UI)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> startActivity(Intent(this@MainActivity,IntroActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }
}
