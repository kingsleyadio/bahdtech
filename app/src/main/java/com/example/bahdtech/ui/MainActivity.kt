package com.example.bahdtech.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bahdtech.R
import com.example.bahdtech.databinding.MainActivityBinding
import com.example.bahdtech.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
