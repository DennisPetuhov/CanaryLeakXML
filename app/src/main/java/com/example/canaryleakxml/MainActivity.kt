package com.example.canaryleakxml

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.canaryleakxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private var binding: ActivityMainBinding? = null
    private val handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button()
        // Static reference to a view
        staticTextView = findViewById<TextView>(R.id.textView)

        // Anonymous inner class holding a reference to the activity
        runnable = Runnable {
            // Do something
        }
        handler.postDelayed(runnable!!, 1000)

        // Non-static inner class instance
        val innerClass: InnerClass = InnerClass()

    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet,
    ): View? {

        return super.onCreateView(parent, name, context, attrs)
    }

    fun button() {
        val button = binding!!.button
        button.setOnClickListener {
            mainActivity = this@MainActivity
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private inner class InnerClass

    companion object {
        private var mainActivity: Context? = null
        private var staticTextView: TextView? = null


    }

}