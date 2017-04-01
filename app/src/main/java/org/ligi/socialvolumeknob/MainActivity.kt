package org.ligi.socialvolumeknob

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        croller.setOnProgressChangedListener {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, it, AudioManager.FLAG_SHOW_UI)
        }

    }
}
