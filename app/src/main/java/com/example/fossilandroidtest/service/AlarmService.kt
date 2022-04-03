package com.example.fossilandroidtest.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.WakeUpActivity
import com.example.fossilandroidtest.common.Constant

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: Entry")
        mediaPlayer = getMediaPlayer()
        vibrator = getVibrator()
    }

    private fun getMediaPlayer() = MediaPlayer.create(this, R.raw.ringtone).apply { isLooping = true }

    private fun getVibrator() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: Entry - Checking ID: $startId")
        val intentActivity = Intent(this, WakeUpActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intentActivity, 0)

        mediaPlayer.start()
        vibrate()

        startForeground(1, getNotification(pendingIntent))
        Log.i(TAG, "onStartCommand: End")
        return START_STICKY
    }

    private fun vibrate() {
        val pattern = longArrayOf(0, 1000, 1000, 1000, 1000)
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, 0)
        }
    }

    private fun getNotification(pendingIntent: PendingIntent?) = NotificationCompat.Builder(this, Constant.CHANNEL_ID)
        .setContentTitle("Hello")
        .setContentText(this.resources.getString(R.string.content_wake_up))
        .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
        .setContentIntent(pendingIntent)
        .build()

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: Entry")
        mediaPlayer.stop()
        vibrator.cancel()
    }

    companion object {
        private val TAG = AlarmService::class.simpleName
    }
}