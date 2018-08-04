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
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import android.arch.lifecycle.Observer
import com.kissedcode.finance.R
import com.kissedcode.finance.model.entity.MyTransaction
import com.kissedcode.finance.model.entity.Wallet
import kotlinx.android.synthetic.main.dialog_operation.*
import java.util.Calendar

class OperationDialog : DialogFragment() {

    lateinit var title: String

    private lateinit var operationViewModel: OperationViewModel

    private lateinit var currencyViewModel: CurrencyViewModel

    private lateinit var transactionViewModel: WalletTransactionViewModel

    private lateinit var wallet: Wallet

    private val categoryList: Observer<List<Category>> = Observer { res ->
        if(res != null) {
            spinnerTransactionCategory.adapter = CategorySpinnerAdapter(context, res)
        }
    }

    private val currencyList: Observer<List<Currency>> = Observer { res ->
        if(res != null) {
            spinnerTransactionCurrency.adapter = CurrencySpinnerAdapter(context, res)
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
        wallet = arguments?.getSerializable(WALLET_KEY) as Wallet
        title = wallet.name
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
        transactionViewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(WalletTransactionViewModel::class.java)

        operationViewModel.type.value = OperationType.SPEND
        spinnerOperationType.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, enumValues<OperationType>() )

        spinnerOperationType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                operationViewModel.type.value = spinnerOperationType.selectedItem as OperationType?
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }

        btnCreateTransaction.setOnClickListener {
            buildTransaction()
        }

    }

    private fun buildTransaction() {
        val c = spinnerTransactionCategory.selectedItem as Category
        val sTC = spinnerTransactionCurrency.selectedItem as Currency
        val transaction = MyTransaction(null,
                date = Calendar.getInstance().time,
                amount = etTransactionSum.text.toString().toDouble(),
                categoryID = c.id!!,
                currencyID = sTC.id!!,
                walletID = wallet.id!!)
        transactionViewModel.addTransaction(transaction, wallet, currencyViewModel.currency.value!!, c.type)
        dialog.dismiss()
    }

    companion object {
        private const val WALLET_KEY = "walletKey"

        fun newInstance(wallet: Wallet): OperationDialog {
            val bundle = Bundle()
            bundle.putSerializable(WALLET_KEY, wallet)
            val operationDialog = OperationDialog()
            operationDialog.arguments = bundle
            return operationDialog
        }
    }
}