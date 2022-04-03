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
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

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
        val intentActivity = Intent(this, WakeUpActivity::class.java).apply { setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP) }
        val pendingIntent = PendingIntent.getActivity(this, 0, intentActivity, 0)
        var alarmName = Constant.DEFAULT_ALARM_NAME

        intent?.run { alarmName = resources.getString(R.string.noti_alarm_name, getStringExtra(Constant.ALARM_NAME)) }

        mediaPlayer.start()
        vibrate()

        startForeground(1, getNotification(pendingIntent, alarmName))
        Log.i(TAG, "onStartCommand: End")
        return START_STICKY
    }

    private fun vibrate() {
        val delay = 0L
        val vibrateTime = 1000L
        val sleep = 1000L
        val pattern = longArrayOf(delay, vibrateTime, sleep, vibrateTime, sleep, vibrateTime, sleep)

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, 0)
        }

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable { vibrate() }
        handler.postDelayed(runnable, 3000)
    }

    private fun getNotification(pendingIntent: PendingIntent?, alarmName: String) = NotificationCompat.Builder(this, Constant.CHANNEL_ID)
        .setContentTitle(alarmName)
        .setContentText(this.resources.getString(R.string.content_wake_up))
        .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
        .setContentIntent(pendingIntent)
        .build()

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: Entry")
        mediaPlayer.stop()
        vibrator.cancel()
        handler.removeCallbacks(runnable)
    }

    companion object {
        private val TAG = AlarmService::class.simpleName
    }
}