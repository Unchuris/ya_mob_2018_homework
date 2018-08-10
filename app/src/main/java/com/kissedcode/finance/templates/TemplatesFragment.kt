package com.kissedcode.finance.templates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kissedcode.finance.R
import com.kissedcode.finance.about.DefaultChildFragment
import com.kissedcode.finance.accounts.operation.OperationDialog
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity
import com.kissedcode.finance.model.entity.IdleTransaction
import kotlinx.android.synthetic.main.fragment_templates.templatesList

class TemplatesFragment: DrawerFragment(), TemplatesListAdapter.RecycleOnClickListenerCallback  {

    override fun openFragment(transaction: IdleTransaction) {
        if (resources.getBoolean(R.bool.is_tablet)) {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer_child, OperationDialog.newInstance(transaction))
                    .addToBackStack(null)
                    .commit()
        } else {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, OperationDialog.newInstance(transaction))
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onTemplateDelete(transaction: IdleTransaction) {
        viewModel.onTemplateDelete(transaction)
    }

    override fun onApplyTemplate(transaction: IdleTransaction) {
        viewModel.onApplyTemplate(transaction)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_templates

    override fun getTitleRes(): Int = R.string.screen_title_templates

    companion object {
        fun newInstance(): TemplatesFragment {
            return TemplatesFragment()
        }
    }

    var templatesAdapter: TemplatesListAdapter? = null

    private val templates = Observer<List<IdleTransaction>> { res ->
        if (res !== null) {
            templatesAdapter?.list = res as MutableList
            templatesAdapter?.notifyDataSetChanged()
        }
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(TemplatesListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        templatesAdapter = TemplatesListAdapter(this)
        templatesList.layoutManager = LinearLayoutManager(
                activity!!,
                LinearLayoutManager.VERTICAL,
                false)
        templatesList.adapter = templatesAdapter
        if (resources.getBoolean(R.bool.is_tablet)) {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer_child, DefaultChildFragment.newInstance())
                    .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.templates.observe(this, this.templates)
    }

    override fun onStop() {
        super.onStop()
        viewModel.templates.removeObservers(this)
    }

}