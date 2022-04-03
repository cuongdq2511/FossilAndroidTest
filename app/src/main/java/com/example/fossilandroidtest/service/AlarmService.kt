package com.example.fossilandroidtest.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.receiver.NotificationReceiver

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
        var alarmName = Constant.DEFAULT_ALARM_NAME

        intent?.run { alarmName = resources.getString(R.string.noti_alarm_name, getStringExtra(Constant.ALARM_NAME)) }

        mediaPlayer.start()
        vibrate()

        startForeground(1, getNotification(alarmName))
        Log.i(TAG, "onStartCommand: End")
        return START_STICKY
    }

    private fun vibrate() {
        val pattern = longArrayOf(0, 400, 1000, 600, 1000, 800, 1000, 1000)
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 3))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, 3)
        }
    }

    private fun getNotification(alarmName: String): Notification {
        val intentDismiss = Intent().apply {
            action = Constant.DISMISS_ACTION
            setClass(this@AlarmService, NotificationReceiver::class.java)
        }
        val pendingDismiss = PendingIntent.getBroadcast(this, SystemClock.uptimeMillis().toInt(), intentDismiss, PendingIntent.FLAG_UPDATE_CURRENT)

        val intentSnooze = Intent().apply {
            action = Constant.SNOOZE_ACTION
            setClass(this@AlarmService, NotificationReceiver::class.java)
        }
        val pendingSnooze = PendingIntent.getBroadcast(this, SystemClock.uptimeMillis().toInt(), intentSnooze, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, Constant.CHANNEL_ID)
            .setContentTitle(alarmName)
            .setContentText(this.resources.getString(R.string.content_wake_up))
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_baseline_alarm_off_24, resources.getString(R.string.dismiss), pendingDismiss)
            .addAction(R.drawable.ic_baseline_snooze_24, resources.getString(R.string.snooze), pendingSnooze)
            .build()
    }

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