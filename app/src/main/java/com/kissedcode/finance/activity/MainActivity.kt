package com.kissedcode.finance.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kissedcode.finance.R
import com.kissedcode.finance.model.Currency
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

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ui
        setupUi()

        // livedata
        bindLiveData()
    }

    private fun setupUi() {

        // currency spinner
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currencySelected(p2)
            }
        }
    }

    private fun bindLiveData() {

        viewModel.balance.observe(
                this, Observer { value: Double? -> balanceTv.text = "${value?:0.0}" }
        )

        viewModel.currencies.observe(
                this,
                Observer { values: Array<Currency>? ->
                        spinnerAdapter.addAll(values?.map { currency -> currency.toString() })
                        spinnerAdapter.notifyDataSetChanged() }
        )

//        viewModel.currency.observe(
//                this, Observer { value: Currency? -> balanceTv.text = "${value?:0.0}" }
//        )
    }

    // controller //////////////////////////////////////////////////////////////////////////////

    private fun currencySelected(index: Int) {
        viewModel.onCurrencySelected(index)
    }

}
