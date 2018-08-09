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
import com.kissedcode.finance.accounts.operation.OperationDialog
import com.kissedcode.finance.databinding.FragmentTemplatesBinding
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.main_screen.MainActivity
import com.kissedcode.finance.utils.autoCleared

class TemplatesFragment: Fragment() {

    companion object {
        fun newInstance(): TemplatesFragment {
            return TemplatesFragment()
        }
    }

    private var binding: FragmentTemplatesBinding by autoCleared()
    private lateinit var viewModel: TemplatesListViewModel
    private var open: Boolean = true

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
        (activity as MainActivity).updateToolBar(R.string.screen_title_templates)
        binding.templatesList.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(TemplatesListViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.open.observe({ lifecycle }) {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, OperationDialog.newInstance(it!!))
                    .addToBackStack(null)
                    .commit()
        }
    }
}