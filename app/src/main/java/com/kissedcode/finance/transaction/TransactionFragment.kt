package com.kissedcode.finance.transaction

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.databinding.FragmentTransactionBinding
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.utils.autoCleared

class TransactionFragment : Fragment() {

    companion object {
        fun newInstance(): TransactionFragment {
            return TransactionFragment()
        }
    }

    private var binding: FragmentTransactionBinding by autoCleared()
    private lateinit var viewModel: TransactionListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_transaction,
                container,
                false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.postList.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(TransactionListViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }
}
