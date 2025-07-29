package com.example.canaryleakxml

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.canaryleakxml.databinding.ActivityMain2Binding
import leakcanary.AppWatcher.objectWatcher

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Create memory leak - static reference to activity
        mainActivity2 = this@MainActivity2
        
        // Anonymous inner class holding reference to activity
        runnable = Runnable {
            // This runnable holds an implicit reference to MainActivity2
            binding.textView2.text = "Leak created!"
        }
        handler.postDelayed(runnable!!, 5000)
        
        // Watch the activity for leaks
        objectWatcher.watch(
            watchedObject = this,
            description = "MainActivity2 memory leak"
        )

        binding.button2.setOnClickListener {
            // Create another leak - static reference to context
            staticContext = this@MainActivity2
            
            // Create inner class instance that holds reference to outer class
            val innerLeak = InnerLeakClass()
            staticInnerLeak = innerLeak
            
            // Watch the inner class for leaks
            objectWatcher.watch(
                watchedObject = innerLeak,
                description = "InnerLeakClass memory leak"
            )
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Don't remove the runnable - this creates the leak!
        // handler.removeCallbacks(runnable!!)
    }
    
    // Non-static inner class - holds implicit reference to MainActivity2
    private inner class InnerLeakClass {
        fun doSomething() {
            // Access outer class members
            binding.textView2.text = "Inner class accessed!"
        }
    }

    companion object {
        private var mainActivity2: Context? = null
        private var staticContext: Context? = null
        private var staticInnerLeak: InnerLeakClass? = null
    }
}