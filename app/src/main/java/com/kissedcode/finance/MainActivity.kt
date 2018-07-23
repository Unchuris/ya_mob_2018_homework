package com.kissedcode.finance

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val spinnerAdapter by lazy {
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        currencySpinner.adapter = adapter
        adapter
    }

    val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    // options menu ////////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menuitem_about -> startActivity(
                    Intent(this, AboutActivity::class.java)
            )
            R.id.menuitem_settings -> startActivity(
                    Intent(this, SettingsActivity::class.java)
            )
        }

        return true
    }

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ui
        //setupUi()

        // livedata
        bindLiveData()
    }

    private fun setupUi() {

        // currency spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.currencies,
                //R.layout.spinner_item)
                android.R.layout.simple_spinner_item)


        //spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter = spinnerAdapter
    }

    private fun bindLiveData() {

        viewModel.balance.observe(
                this, Observer { value: Double? -> balanceTv.text = "${value?:0.0}" }
        )

        viewModel.currencies.observe(
                this, Observer { values: Array<String>? -> if (values != null) { spinnerAdapter.addAll(*values); spinnerAdapter.notifyDataSetChanged() } }
        )

//        viewModel.currency.observe(
//                this, Observer { value: Currency? -> balanceTv.text = "${value?:0.0}" }
//        )
    }
}
