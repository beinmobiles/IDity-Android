package com.mmh.test.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.beinmobiles.idity.*
import com.beinmobiles.idity.model.*
import com.mmh.test.R
class MainActivity : AppCompatActivity(), IDityJourneyCallbacks {

    private lateinit var captureButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureButton = findViewById(R.id.captureButton)
        captureButton.setOnClickListener {
            didTapCapture()
        }
    }

    private fun didTapCapture() {
        // Start the SDK journey
        IDitySDK.startJourney(this, this)
    }

    // --- IDityJourneyCallbacks Implementation ---

    override fun onJourneyStarted() {
        // Handle journey start if needed
    }

    override fun onJourneyCompleted(infoObject: InfoObject) {
        // Navigation in Android: Use Intents to move to the next Activity
        val intent = Intent(this, DynamicFormActivity::class.java).apply {
        }
        startActivity(intent)
    }

    override fun onJourneyCancelled() {
        // Handle cancellation
    }

    override fun onError(error: Exception) {
        // Handle error (e.g., show a Toast or Dialog)
    }
}