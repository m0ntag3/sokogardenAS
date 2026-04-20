package com.example.sokogarden

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class About : AppCompatActivity() {

    // Declare a variable that'll hold the Text to Speech object
    lateinit var tts : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the TextView and the Button using their ids
        val textView = findViewById<TextView>(R.id.abouttxt)
        val speakBtn = findViewById<Button>(R.id.btnListen)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // 1. Setting accent/language first
                tts.language = Locale.US

                // 2. Specifying voice
                val voices = tts.voices
                if (voices != null) {
                    for (voice in voices) {
                        // Filter by name (e.g., "en-us-x-sfg-local") or characteristics
                        // Most modern Google TTS voices include "male" or "female" in the description
                        if (voice.name.contains("female", ignoreCase = true) && voice.locale == Locale.US) {
                            tts.voice = voice
                            break
                        }
                    }
                }
            }
        }

        // end
        speakBtn.setOnClickListener {
            val text = textView.text.toString()
            // QUEUE_FLUSH - frees the mic from its current use in another app availing it for use in our app
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
        // Stops the text from being read after the user has closed the activity/page
    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}