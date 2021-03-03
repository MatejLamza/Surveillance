package com.example.surveillance.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.surveillance.data.remote.response.PlateAPIItem

class MyBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent!!.extras
        val plate = bundle?.getParcelable("Plate") as PlateAPIItem?
        Log.d("bbb", "Plate found: $plate")
    }
}