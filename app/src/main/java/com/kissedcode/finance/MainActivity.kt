package com.kissedcode.finance

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // options menu ////////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_options, menu)
        return true
    }

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ui
        setupUi()
    }

    private fun setupUi() {

        // currency spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.currencies,
                R.layout.spinner_item)

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        currencySpinner.adapter = spinnerAdapter
    }
}
