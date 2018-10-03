package com.kissedcode.finance.accounts.operation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency

class ItemSpinnerAdapter<T>(context: Context?, private val items: List<T>)
    : ArrayAdapter<T>(context, android.R.layout.simple_spinner_dropdown_item, items) {

    private fun getCustomView(position: Int, convertView: View?): View? {
        var view = convertView
        if (view == null)
            view = View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null)

        val item = items[position]
        (view as TextView).text =
                when (item) {
                    is Category -> item.categoryName
                    is Currency -> item.currencyName
                    else -> null
                }
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, convertView)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, convertView)
    }
}
