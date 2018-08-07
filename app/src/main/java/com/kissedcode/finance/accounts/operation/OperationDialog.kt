package com.kissedcode.finance.accounts.operation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kissedcode.finance.R
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.database.AppDatabase
import com.kissedcode.finance.model.entity.Category
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.IdleDeferTransaction
import com.kissedcode.finance.model.entity.IdleWallet
import com.kissedcode.finance.model.entity.MyTransaction
import kotlinx.android.synthetic.main.dialog_operation.accountNameTv
import kotlinx.android.synthetic.main.dialog_operation.btnCreateTemplate
import kotlinx.android.synthetic.main.dialog_operation.btnCreateTransaction
import kotlinx.android.synthetic.main.dialog_operation.etTransactionRepeat
import kotlinx.android.synthetic.main.dialog_operation.etTransactionSum
import kotlinx.android.synthetic.main.dialog_operation.spinnerOperationType
import kotlinx.android.synthetic.main.dialog_operation.spinnerTransactionCategory
import kotlinx.android.synthetic.main.dialog_operation.spinnerTransactionCurrency
import java.util.Calendar

class OperationDialog : DialogFragment() {

    lateinit var title: String

    private lateinit var operationViewModel: OperationViewModel

    private lateinit var wallet: IdleWallet

    private val categoryList: Observer<List<Category>> = Observer { res ->
        if (res != null) {
            spinnerTransactionCategory.adapter = CategorySpinnerAdapter(context, res)
        }
    }

    private val currencyList: Observer<List<Currency>> = Observer { res ->
        if (res != null) {
            spinnerTransactionCurrency.adapter = CurrencySpinnerAdapter(context, res)
        }
    }

    override fun onStart() {
        super.onStart()
        operationViewModel.categories.observe(this, categoryList)
        operationViewModel.currency.observe(this, currencyList)
    }

    override fun onStop() {
        super.onStop()
        operationViewModel.categories.removeObservers(this)
        operationViewModel.currency.removeObservers(this)
    }

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wallet = arguments?.getSerializable(WALLET_KEY) as IdleWallet
        title = wallet.walletName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(activity as AppCompatActivity)
        return inflater.inflate(R.layout.dialog_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountNameTv.text = title
        operationViewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(OperationViewModel::class.java)
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
        btnCreateTemplate.setOnClickListener{
            buildTemplate()
        }
    }

    private fun buildTemplate() {
        val c = spinnerTransactionCategory.selectedItem as Category
        val sTC = spinnerTransactionCurrency.selectedItem as Currency
        val transaction = createTransaction(c, sTC, template = true)
        operationViewModel.addToTemplate(transaction)
    }

    private fun buildTransaction() {
        val c = spinnerTransactionCategory.selectedItem as Category
        val sTC = spinnerTransactionCurrency.selectedItem as Currency
        val transaction = createTransaction(c, sTC)
        operationViewModel.addTransaction(transaction, wallet, operationViewModel.currency.value!!, c.categoryType)
        if (etTransactionRepeat.text.toString() != "") {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, etTransactionRepeat.text.toString().toInt())
            val t = IdleDeferTransaction(
                    null,
                    idleDeferTransactionDate = Calendar.getInstance().time,
                    idleDeferTransactionAmount = etTransactionSum.text.toString().toDouble(),
                    currencyID = sTC.currencyId!!,
                    categoryID = c.categoryId!!,
                    walletID = wallet.IdleWalletId!!,
                    repeatDays = etTransactionRepeat.text.toString().toInt(),
                    nextRepeatDay = calendar.get(Calendar.DAY_OF_MONTH),
                    nextRepeatMonth = calendar.get(Calendar.MONTH),
                    nextRepeatYear = calendar.get(Calendar.YEAR)
            )
            db.getDeferTransactionDao().insert(t)
        }
        dialog.dismiss()
    }

    private fun createTransaction(c: Category, sTC: Currency, template: Boolean = false): MyTransaction {
        return MyTransaction(null,
                myTransactionDate = Calendar.getInstance().time,
                myTransactionAmount = etTransactionSum.text.toString().toDouble(),
                categoryID = c.categoryId!!,
                currencyID = sTC.currencyId!!,
                walletID = wallet.IdleWalletId!!,
                template = template)
    }

    companion object {
        private const val WALLET_KEY = "walletKey"

        fun newInstance(wallet: IdleWallet): OperationDialog {
            val bundle = Bundle()
            bundle.putSerializable(WALLET_KEY, wallet)
            val operationDialog = OperationDialog()
            operationDialog.arguments = bundle
            return operationDialog
        }
    }
}
