package com.example.githubclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {
    private var binding: ActivityMainBinding? = null
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initViews()
    }

    private fun initViews() {
        binding?.counterButton1?.setOnClickListener {
            presenter.incrementCounter1()
        }
        binding?.counterButton2?.setOnClickListener {
            presenter.incrementCounter2()
        }
        binding?.counterButton3?.setOnClickListener {
            presenter.incrementCounter3()
        }
    }

    override fun setButton1Text(text: String) {
        binding?.counterButton1?.text = text
    }

    override fun setButton2Text(text: String) {
        binding?.counterButton2?.text = text
    }

    override fun setButton3Text(text: String) {
        binding?.counterButton3?.text = text
    }


}