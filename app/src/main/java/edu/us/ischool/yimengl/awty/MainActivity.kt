package edu.us.ischool.yimengl.awty

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = findViewById<EditText>(R.id.txtMessage)
        val phone = findViewById<EditText>(R.id.txtPhone)
        val frequency = findViewById<EditText>(R.id.txtFrequncy)
        val btnStart = findViewById<Button>(R.id.btnStart)
        var isStart = true
        val alarmManager: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,MessageActivity::class.java)
        btnStart.isEnabled = false

        message.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (isNotEmpty(message,phone,frequency)) {
                    btnStart.isEnabled = true
                }
            }
        })

        phone.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (isNotEmpty(message,phone,frequency)) {
                    btnStart.isEnabled = true
                }
            }
        })

        frequency.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (isNotEmpty(message,phone,frequency)) {
                    btnStart.isEnabled = true
                }
            }
        })

        btnStart.setOnClickListener{
            if (isStart) {
                if (isNotEmpty(message, phone, frequency) && isFrequencyValid(frequency) && isPhoneValid(phone)) {
                    btnStart.text = "Stop"
                    isStart = !isStart
                    intent.putExtra("message",message.text.toString())
                    intent.putExtra("phone",phone.text.toString())
                    val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
                    val messageFrequency = frequency.text.toString().toLong() * 60 * 1000
                    alarmManager.setRepeating(RTC_WAKEUP, System.currentTimeMillis(),messageFrequency,pendingIntent)
                } else {
                    showErrors(message, phone, frequency)
                }
            } else {
                btnStart.text = "Start"
                isStart = !isStart
                val pendingIntent = PendingIntent.getBroadcast(applicationContext,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)
                alarmManager.cancel(pendingIntent)
            }
        }
    }
    private fun isNotEmpty(message: EditText,phone: EditText, frequency: EditText): Boolean {
        return message.text.isNotEmpty() && phone.text.isNotEmpty() && frequency.text.isNotEmpty()
    }

    private fun isPhoneValid(phone: EditText): Boolean {
        val phoneNumber = phone.text.toString()
        return phoneNumber.length == 10
    }

    private fun isFrequencyValid(frequency: EditText): Boolean {
        return frequency.text.toString().toInt() > 0
    }

    private fun showErrors(message: EditText, phone: EditText, frequency: EditText){
        if (!isNotEmpty(message, phone, frequency)) {
            Toast.makeText(this, "Please fill all the required fields!", Toast.LENGTH_SHORT).show()
        }
        if (!isFrequencyValid(frequency)) {
            Toast.makeText(this,"Please enter a valid number!", Toast.LENGTH_SHORT).show()
        }
        if (!isPhoneValid(phone)) {
            Toast.makeText(this,"Please enter a valid phone number!", Toast.LENGTH_SHORT).show()
        }
    }
}
