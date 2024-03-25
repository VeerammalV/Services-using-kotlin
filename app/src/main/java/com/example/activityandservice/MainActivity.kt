package com.example.activityandservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.activityandservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startStopService()
        }
    }

    private fun startStopService() {
        if (isMyServiceRunning()) {

            Toast.makeText(this,"Service Stopped",Toast.LENGTH_SHORT).show()
            stopService(Intent(this,MyService::class.java))
        } else {
            Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show()
            startService(Intent(this,MyService::class.java))
        }
    }

    private fun isMyServiceRunning():Boolean {

        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for(service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)) {

            if(MyService::class.java.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }
}

