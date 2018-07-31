package com.kissedcode.finance.operation

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kissedcode.finance.R
import com.kissedcode.finance.model.MoneyOperation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_operation.*

class OperationDialog : DialogFragment() {

    lateinit var title: String

    lateinit var spinnerAdapter: ArrayAdapter<String>

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

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
        accountNameTv.text = title

        // categories spinner
        val adapter = ArrayAdapter<String>(context, R.layout.spinner_item)
        adapter.addAll(arrayListOf(*resources.getStringArray(R.array.categories)))
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    // support classes /////////////////////////////////////////////////////////////////////////

    interface Callback {
        fun onNewOperation(operation: MoneyOperation)
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