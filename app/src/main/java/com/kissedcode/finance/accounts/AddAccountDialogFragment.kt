package com.kissedcode.finance.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.accounts.operation.CurrencySpinnerAdapter
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.model.entity.Currency
import com.kissedcode.finance.model.entity.Wallet
import kotlinx.android.synthetic.main.dialog_new_wallet.btnCreateWallet
import kotlinx.android.synthetic.main.dialog_new_wallet.etWalletAmount
import kotlinx.android.synthetic.main.dialog_new_wallet.etWalletName
import kotlinx.android.synthetic.main.dialog_new_wallet.spinnerWalletCurrency

class AddAccountDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): AddAccountDialogFragment {
            return AddAccountDialogFragment()
        }
    }

    private lateinit var addAccountViewModel: AddAccountViewModel

    private val currencyList: Observer<List<Currency>> = Observer { res ->
        if (res != null) {
            spinnerWalletCurrency.adapter = CurrencySpinnerAdapter(context, res)
        }
    }

    override fun onStart() {
        super.onStart()
        addAccountViewModel.currency.observe(this, currencyList)
    }

    override fun onStop() {
        super.onStop()
        addAccountViewModel.currency.removeObservers(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_new_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAccountViewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(AddAccountViewModel::class.java)

        btnCreateWallet.setOnClickListener {
            buildWallet()
        }
    }

    private fun buildWallet() {
        if (!etWalletAmount.text.isEmpty()) {
            val sTC = spinnerWalletCurrency.selectedItem as Currency
            val wallet = Wallet(
                    walletId = null,
                    walletValue = etWalletAmount.text.toString().toDouble(),
                    walletName = etWalletName.text.toString(),
                    currencyID = sTC.currencyId!!
            )
            addAccountViewModel.insert(wallet)
        }
        dialog.dismiss()
    }
}