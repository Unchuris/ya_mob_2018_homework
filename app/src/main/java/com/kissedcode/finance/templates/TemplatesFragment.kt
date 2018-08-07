package com.kissedcode.finance.templates

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
import com.kissedcode.finance.databinding.FragmentTemplatesBinding
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.utils.autoCleared

class TemplatesFragment: Fragment() {

    companion object {
        fun newInstance(): TemplatesFragment {
            return TemplatesFragment()
        }
    }

    private var binding: FragmentTemplatesBinding by autoCleared()
    private lateinit var viewModel: TemplatesListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_templates,
                container,
                false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.templatesList.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(TemplatesListViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }
}