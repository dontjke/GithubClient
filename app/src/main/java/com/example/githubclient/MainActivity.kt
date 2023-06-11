package com.example.githubclient

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {
    private var binding: ActivityMainBinding? = null
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val listener = View.OnClickListener {
            presenter.counterClick(it.id)
        }

        binding?.counterButton1?.setOnClickListener(listener)
        binding?.counterButton2?.setOnClickListener(listener)
        binding?.counterButton3?.setOnClickListener(listener)
    }

    override fun setButtonText(index: Int, text: String) {
        when (index) {
            0 -> binding?.counterButton1?.text = text
            1 -> binding?.counterButton2?.text = text
            2 -> binding?.counterButton3?.text = text
        }
    }
}