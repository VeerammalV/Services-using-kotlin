package com.example.activityandservice

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.activityandservice.Constants.CHANNEL_ID
import com.example.activityandservice.Constants.MUSIC_NOTIFICATION_ID

class MyService: Service(){

    private var musicPlayer:MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags:Int, startId:Int):Int {
        showNotification()

        if(musicPlayer?.isPlaying == true){
            musicPlayer?.stop()
        } else {
            musicPlayer?.start()
        }
        return  START_STICKY
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        showNotification()
//
//        return  START_STICKY
//
//        return super.onStartCommand(intent, flags, startId)
//    }

    @SuppressLint("ForegroundServiceType", "UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(){
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,0,notificationIntent,0)

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My Serivce Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun initMusic(){
        musicPlayer = MediaPlayer.create(this,
            R.raw.mymusic)

        musicPlayer?.isLooping = true
        musicPlayer?.setVolume(100F,100F)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (musicPlayer != null) {
            musicPlayer?.stop()
        }
    }
}