package com.example.githubclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.utils.KEY_BUNDLE_COUNTERS

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private val counters = mutableListOf(0,0,0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.counterButton1?.setOnClickListener {
            binding?.counterButton1?.text = ((++counters[0]).toString())
        }
        binding?.counterButton2?.setOnClickListener {
            binding?.counterButton2?.text = ((++counters[1]).toString())
        }
        binding?.counterButton3?.setOnClickListener {
            binding?.counterButton3?.text = ((++counters[2]).toString())
        }
        initViews()
    }

    private fun initViews(){
        binding?.counterButton1?.text = counters[0].toString()
        binding?.counterButton2?.text = counters[1].toString()
        binding?.counterButton3?.text = counters[2].toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(KEY_BUNDLE_COUNTERS,counters.toIntArray())
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putIntArray(KEY_BUNDLE_COUNTERS,counters.toIntArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val countersArray = savedInstanceState.getIntArray(KEY_BUNDLE_COUNTERS)
        countersArray?.toList()?.let {
            counters.clear()
            counters.addAll(it)
        }
        initViews()
    }
}