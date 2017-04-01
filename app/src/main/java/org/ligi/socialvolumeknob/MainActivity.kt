package org.ligi.socialvolumeknob

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    val audioManager by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        croller.label = State.identifier
        croller.labelSize = resources.getDimension(R.dimen.croller_label_size).toInt()

        val relevantStream = AudioManager.STREAM_MUSIC
        val maxVolume = audioManager.getStreamMaxVolume(relevantStream)
        val currentVolume = audioManager.getStreamVolume(relevantStream)

        croller.progress = ((currentVolume.toDouble()/maxVolume)*croller.max).toInt()

        croller.setOnProgressChangedListener {
            setVolume(it.toDouble() / croller.max)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(Intent(this@MainActivity, IntroActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
        super.onResume()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe
    fun volumeEvent(volumeEvent: VolumeEvent) {
        setVolume(volumeEvent.volume)
        runOnUiThread {
            croller.progress = (croller.max * volumeEvent.volume).toInt()
            croller.invalidate()
        }

    }

    private fun setVolume(volume: Double) {
        val relevantStream = AudioManager.STREAM_MUSIC
        val maxVolume = audioManager.getStreamMaxVolume(relevantStream)
        val newVolume = (maxVolume * volume).toInt()

        audioManager.setStreamVolume(relevantStream, newVolume, AudioManager.FLAG_SHOW_UI)
    }
}
