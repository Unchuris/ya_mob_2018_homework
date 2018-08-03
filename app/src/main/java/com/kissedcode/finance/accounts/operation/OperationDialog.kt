package com.kissedcode.finance.accounts.operation

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kissedcode.finance.R
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import android.arch.lifecycle.Observer
import kotlinx.android.synthetic.main.dialog_operation.*
import java.util.Calendar

class OperationDialog : DialogFragment() {

    lateinit var title: String

    lateinit var spinnerAdapter: ArrayAdapter<String>

    private lateinit var operationViewModel: OperationViewModel

    private lateinit var currencyViewModel: CurrencyViewModel

    private var dateTime = Calendar.getInstance()

    private val categoryList: Observer<List<Category>> = Observer { res ->
        if(res != null) {
            spinnerTransactionCategory.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    private val currencyList: android.arch.lifecycle.Observer<List<Currency>> = Observer { res ->
        if(res != null) {
            spinnerTransactionCurrency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    override fun onStart() {
        super.onStart()
        operationViewModel.categories.observe(this, categoryList)
        currencyViewModel.currency.observe(this, currencyList)
    }

    override fun onStop() {
        super.onStop()
        operationViewModel.categories.removeObservers(this)
        currencyViewModel.currency.removeObservers(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments!!.getString(ARG_ACCOUNT_NAME)!!
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountNameTv.text = title

        operationViewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(OperationViewModel::class.java)
        currencyViewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(CurrencyViewModel::class.java)

        operationViewModel.type.value = OperationType.SPEND
        spinnerOperationType.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, enumValues<OperationType>() )

        spinnerOperationType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                operationViewModel.type.value = spinnerOperationType.selectedItem as OperationType?
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }

        //initSaveButton()
    }

    companion object {

        val ARG_ACCOUNT_NAME = "arg_account_name"

        fun newInstance(accountName: String): OperationDialog {
            val fragment = OperationDialog()

            val args = Bundle()
            args.putString(ARG_ACCOUNT_NAME, accountName)
            fragment.arguments = args

            return fragment
        }
    }
}