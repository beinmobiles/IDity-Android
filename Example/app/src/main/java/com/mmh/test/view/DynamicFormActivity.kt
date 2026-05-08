package com.mmh.test.view

import android.R
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beinmobiles.idity.model.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class DynamicFormActivity : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var container: LinearLayout

    // Your data object
    var infoObject = InfoObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        buildForm(infoObject)
    }

    private fun setupUI() {
        scrollView = ScrollView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setPadding(32, 32, 32, 32)
            clipToPadding = false
        }

        container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        scrollView.addView(container)
        setContentView(scrollView)
    }

    private fun buildForm(obj: Any) {
        // Get all properties of the class using Kotlin Reflection
        val properties = obj::class.declaredMemberProperties

        for (prop in properties) {
            val key = prop.name
            val value = (prop as KProperty1<Any, *>).get(obj) ?: continue

            when (value) {
                is String -> {
                    addStringField(key, value)
                }
                is Bitmap -> {
                    addImageField(key, value)
                }
                // Check if it's a custom nested object (not a primitive/standard type)
                else -> {
                    if (!value::class.java.name.startsWith("android") &&
                        !value::class.java.name.startsWith("java")) {
                        addSectionTitle(key)
                        buildForm(value) // Recursion
                    }
                }
            }
        }
    }

    private fun addSectionTitle(title: String) {
        val textView = TextView(this).apply {
            text = title.uppercase()
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            setPadding(0, 24, 0, 8)
        }
        container.addView(textView)
    }

    private fun addStringField(title: String, value: String) {
        val label = TextView(this).apply {
            text = title
            textSize = 14f
        }

        val editText = EditText(this).apply {
            setText(value)
            isEnabled = true // Set to false if read-only
            setBackgroundResource(R.drawable.edit_text)
        }

        val fieldContainer = createVerticalContainer()
        fieldContainer.addView(label)
        fieldContainer.addView(editText)
        container.addView(fieldContainer)
    }

    private fun addImageField(title: String, bitmap: Bitmap) {
        val label = TextView(this).apply {
            text = title
            textSize = 14f
        }

        val imageView = ImageView(this).apply {
            setImageBitmap(bitmap)
            adjustViewBounds = true
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                450 // roughly 150dp
            )
        }

        val fieldContainer = createVerticalContainer()
        fieldContainer.addView(label)
        fieldContainer.addView(imageView)
        container.addView(fieldContainer)
    }

    private fun createVerticalContainer(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 12, 0, 12)
            layoutParams = params
        }
    }
}