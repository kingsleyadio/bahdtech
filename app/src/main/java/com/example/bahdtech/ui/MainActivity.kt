package com.example.bahdtech.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bahdtech.R
import com.example.bahdtech.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
