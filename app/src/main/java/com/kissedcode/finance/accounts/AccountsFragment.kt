package com.kissedcode.finance.accounts

import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.DrawerFragment
import kotlinx.android.synthetic.main.fragment_accounts.*


class AccountsFragment : DrawerFragment() {

    val viewModel by lazy {
        ViewModelProviders.of(this).get(AccountsViewModel::class.java)
    }

    lateinit var accountsAdapter: AccountsAdapter

    // required overrides //////////////////////////////////////////////////////////////////////

    override fun getLayoutRes() = R.layout.fragment_accounts

    override fun getTitleRes() = R.string.screen_title_accounts

    // lifecycle ///////////////////////////////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // recycler view
        accountsRv.setHasFixedSize(false)
        accountsAdapter = AccountsAdapter(this)
        accountsRv.adapter = accountsAdapter
        accountsRv.layoutManager = LinearLayoutManager(
                activity!!,
                LinearLayoutManager.VERTICAL,
                false)

        // observe viewmodel
        viewModel.accounts.observe({ lifecycle }) { accountsAdapter.data = it!! }
    }

    // controller //////////////////////////////////////////////////////////////////////////////

    fun onNewOperationRequested(account: Account) {
        //Toast.makeText(context, account.name, Toast.LENGTH_SHORT).show()
        fragmentManager!!.beginTransaction()
                .add(OperationDialog.newInstance(account.name), null)
                .addToBackStack(null)
                .commit()
    }

    // support classes /////////////////////////////////////////////////////////////////////////

    class AccountVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById<TextView>(R.id.accountNameTv)
        val balanceTv = itemView.findViewById<TextView>(R.id.accountBalanceTv)
        val iconIv = itemView.findViewById<ImageView>(R.id.accountIconIv)
        val newOperationBtn = itemView.findViewById<ImageButton>(R.id.accountNewOperationIb)
        lateinit var accountsFragment: AccountsFragment
    }

    class AccountsAdapter(val accountsFragment: AccountsFragment) : RecyclerView.Adapter<AccountVH>() {

        var data: List<Account> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, type: Int): AccountVH {
            val viewHolder =  AccountVH(LayoutInflater.from(parent.context)
                    .inflate(R.layout.viewholder_account, parent, false))

            viewHolder.accountsFragment = accountsFragment

            viewHolder.newOperationBtn.setOnClickListener {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    accountsFragment.onNewOperationRequested(data[position])
                }
            }

            return viewHolder
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(viewHolder: AccountVH, position: Int) {
            val account = data[position]

            viewHolder.nameTv.text = account.name
            viewHolder.balanceTv.text = account.balance.toString()
            viewHolder.iconIv.setImageResource(account.iconRes)
        }
    }
}
