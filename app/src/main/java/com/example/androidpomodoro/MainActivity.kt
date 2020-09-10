package com.example.androidpomodoro

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val brakeTime: Int = 3 // 5分
    private val workTime: Int = 15 // 25分
    private var current: Int = workTime
    private var isWorkTime: Boolean = false
    private var isStart: Boolean = true

    private var timer: Timer? = null
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textTitle = findViewById<TextView>(R.id.text_title)
        var textTimer = findViewById<TextView>(R.id.text_timer)
        var startButton = findViewById<FloatingActionButton>(R.id.start_button)
        var stopButton = findViewById<FloatingActionButton>(R.id.stop_button)
        val listener = StartStopListener()

        textTitle.setText(R.string.work_time)
        textTimer.text = formatTime()
        startButton.setOnClickListener(listener)
        stopButton.setOnClickListener(listener)
    }

    private inner class StartStopListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view!!.id) {
                R.id.start_button -> {
                    if (isStart) {
                        isStart = false
                        startTimer()
                    } else {
                        null
                    }
                }
                R.id.stop_button -> {
                    if (!isStart) {
                        resetTimer()
                    } else {
                        null
                    }
                }
            }
        }
    }


    private fun startTimer() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (current == 0) {
                        isStart = true
                        timer!!.cancel()
                        if (isWorkTime) {
                            current = workTime
                            isWorkTime = false
                            text_title.setText(R.string.work_time)
                            text_timer.text = formatTime()
                        } else {
                            current = brakeTime
                            isWorkTime = true
                            text_title.setText(R.string.brake_time)
                            text_timer.text = formatTime()
                        }
                    } else {
                        current--
                        text_timer.text = formatTime()
                    }
                }
            }
        }, 0, 1000)
    }

    private fun formatTime(): String {
        val minutes = (current / 60).toString().padStart(2, '0')
        val seconds = (current % 60).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }

    private fun resetTimer() {
        isStart = true
        timer!!.cancel()
        current = workTime
        isWorkTime = false
        text_title.setText(R.string.work_time)
        text_timer.text = formatTime()
    }

}