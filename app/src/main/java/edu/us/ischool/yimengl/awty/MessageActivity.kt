package edu.us.ischool.yimengl.awty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.widget.Toast.*

class MessageActivity: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra("message")
        val phone = intent!!.getStringExtra("phone")
        val toastMessage = '(' + phone.substring(0,3) + ')' + phone.substring(3,6) + '-' + phone.substring(6,10)+ ": "+ message
        Log.i(javaClass.name,toastMessage)
        Toast.makeText(context, toastMessage,Toast.LENGTH_SHORT).show()
    }
}