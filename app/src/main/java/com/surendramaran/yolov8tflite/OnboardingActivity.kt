package com.surendramaran.yolov8tflite

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class OnboardingActivity : AppCompatActivity() {

    private var currentPage = 0
    private val totalPages = 3

    // UI Elements
    private lateinit var logoImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var headerTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var contentLayout: LinearLayout
    private lateinit var nextButton: Button
    private lateinit var dotIndicator1: MaterialCardView
    private lateinit var dotIndicator2: MaterialCardView
    private lateinit var dotIndicator3: MaterialCardView

    // Preferences for checking first launch
    private lateinit var preferences: SharedPreferences

    // Update your OnboardingActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view right away
        setContentView(R.layout.activity_onboarding_simple)

        // Initialize UI elements
        logoImageView = findViewById(R.id.logoImageView)
        titleTextView = findViewById(R.id.titleTextView)
        headerTextView = findViewById(R.id.headerTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        contentLayout = findViewById(R.id.contentLayout)
        nextButton = findViewById(R.id.nextButton)
        dotIndicator1 = findViewById(R.id.dotIndicator1)
        dotIndicator2 = findViewById(R.id.dotIndicator2)
        dotIndicator3 = findViewById(R.id.dotIndicator3)

        // Setup initial content
        updatePageContent(0)

        // Handle button click
        nextButton.setOnClickListener {
            if (currentPage < totalPages - 1) {
                currentPage++
                updatePageContent(currentPage)
            } else {
                // Last page, go to main activity (without saving any preference)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Simple finish without animations
        } catch (e: Exception) {
            finishAffinity()
        }
    }

    private fun updatePageContent(page: Int) {
        // Update dots indicator
        updateIndicators(page)

        // Update button text on last page
        nextButton.text = if (page == totalPages - 1) "Get Started" else "Next"

        // Update content based on page
        when (page) {
            0 -> {
                // First slide - Introduction
                headerTextView.text = "Breaking communication barriers through technology"

                // Set description text
                descriptionTextView.text = "SIGNIFY is a Filipino Sign Language (FSL) recognition application that helps deaf and mute individuals communicate with those who don't understand sign language.\n\nOur advanced YOLOv11 technology accurately detects hand gestures and facial expressions in real-time, converting them instantly to text or speech.\n\nWe're committed to creating a more inclusive world where everyone can be understood."

                // Clear any bullet points
                contentLayout.removeAllViews()
                descriptionTextView.visibility = View.VISIBLE
            }

            1 -> {
                // Second slide - Key Features
                headerTextView.text = "Key Features"

                // Hide description text and show bullet points
                descriptionTextView.visibility = View.GONE
                contentLayout.removeAllViews()

                // Create bullet points for features
                val features = listOf(
                    "Sign Detection: Accurately identifies FSL letters and words",
                    "Emotion Recognition: Detects facial expressions for proper tone in speech",
                    "Word Formation: \"Capture\" button saves detected signs to form words",
                    "Space Function: Add spaces between words for natural communication",
                    "Expressive Speech: \"Speak\" button uses detected emotion for natural tone"
                )

                // Add bullet points
                for (feature in features) {
                    addBulletPoint(feature)
                }
            }

            2 -> {
                // Third slide - Best Practices
                headerTextView.text = "For best results:"

                // Hide description text and show bullet points
                descriptionTextView.visibility = View.GONE
                contentLayout.removeAllViews()

                // Create bullet points for best practices
                val bestPractices = listOf(
                    "Position camera 3 feet away",
                    "Ensure good lighting on hands and face",
                    "Keep hands clearly visible in the frame",
                    "Make facial expressions visible for full context",
                    "Sign at a natural pace",
                    "One sign at a time for better accuracy",
                    "Use \"Clear\" buttons to restart if needed"
                )

                // Add bullet points
                for (practice in bestPractices) {
                    addBulletPoint(practice)
                }
            }
        }
    }

    private fun addBulletPoint(text: String) {
        // Create text view for bullet point
        val bulletPoint = TextView(this).apply {
            this.text = "• $text"
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            setPadding(0, 0, 0, 24) // Add space between bullet points
        }

        // Add to layout
        contentLayout.addView(bulletPoint)
    }

    private fun updateIndicators(position: Int) {
        val activeColor = ContextCompat.getColor(this, android.R.color.white)
        val inactiveColor = ContextCompat.getColor(this, R.color.translucent_white)

        dotIndicator1.setCardBackgroundColor(if (position == 0) activeColor else inactiveColor)
        dotIndicator2.setCardBackgroundColor(if (position == 1) activeColor else inactiveColor)
        dotIndicator3.setCardBackgroundColor(if (position == 2) activeColor else inactiveColor)
    }

    companion object {
        fun resetOnboardingStatus(context: Context) {
            val prefs = context.getSharedPreferences("SignifyAppPrefs", MODE_PRIVATE)
            prefs.edit().clear().apply()
        }
    }
}