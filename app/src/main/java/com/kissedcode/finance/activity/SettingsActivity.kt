package com.kissedcode.finance.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kissedcode.finance.R

class SettingsActivity : AppCompatActivity() {

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}