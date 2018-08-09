package com.kissedcode.finance.accounts

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.kissedcode.finance.R
import com.kissedcode.finance.accounts.operation.OperationDialog
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.main_screen.DrawerFragment
import com.kissedcode.finance.main_screen.MainActivity
import com.kissedcode.finance.model.entity.IdleWallet
import kotlinx.android.synthetic.main.dialog_edit_wallet.view.wallet_name


class AccountsFragment : DrawerFragment() {

    override fun getLayoutRes() = R.layout.fragment_accounts

    override fun getTitleRes() = R.string.screen_title_accounts

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).updateToolBar(resId)
    }

    companion object {
        fun newInstance(): AccountsFragment {
            return AccountsFragment()
        }
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(AccountsViewModel::class.java)
    }

    private fun showDeleteFragmentDialog(id: Int) {
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_wallet)
                .setPositiveButton(R.string.ok) {_, _ ->
                    viewModel.deleteWallet(id)
                }
                .setNegativeButton(R.string.cancel){ _, _ -> }
                .create()
                .show()
    }

    private fun showEditFragmentDialog(id: Int) {
        val editDialog = layoutInflater.inflate(R.layout.dialog_edit_wallet, null)
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.wallet_title_edit)
                .setView(editDialog)
                .setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.updateWallet(id, editDialog.wallet_name.text.toString())
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()
                .show()
    }

    private lateinit var accountsAdapter: AccountsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (resources.getBoolean(R.bool.is_tablet)) {
            (activity as MainActivity).openScreen(R.id.transition_current_scene, false)
        }
        accountsRv.setHasFixedSize(false)
        accountsAdapter = AccountsAdapter(this, { showDeleteFragmentDialog(it) }, { showEditFragmentDialog(it) })
        accountsRv.adapter = accountsAdapter
        accountsRv.layoutManager = LinearLayoutManager(
                activity!!,
                LinearLayoutManager.VERTICAL,
                false)

        viewModel.accounts.observe({ lifecycle }) {
            accountsAdapter.data = it!!
            accountsAdapter.notifyDataSetChanged()
        }
        addWallet.setOnClickListener {
            AddAccountDialogFragment.newInstance().show(childFragmentManager, null)
        }
    }

    fun onNewOperationRequested(account: IdleWallet) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, OperationDialog.newInstance(account))
                .addToBackStack(null)
                .commit()
    }

    class AccountsAdapter(private val accountsFragment: AccountsFragment,
                          private val onDeleteAction: (position: Int) -> Unit,
                          private val onEditAction: (position: Int) -> Unit) : RecyclerView.Adapter<AccountsAdapter.AccountVH>() {

        var data: List<IdleWallet> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, type: Int): AccountVH {
            val viewHolder = AccountVH(LayoutInflater.from(parent.context)
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

        inner class AccountVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

            override fun onCreateContextMenu(contextMenu: ContextMenu, selectedView: View, menuInfo: ContextMenu.ContextMenuInfo?) {
                contextMenu.add(0, selectedView.id, 0, R.string.edit).setOnMenuItemClickListener(this)
                contextMenu.add(0, selectedView.id, 1,  R.string.delete).setOnMenuItemClickListener(this)
            }

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    when (menuItem.order) {
                        0 -> onEditAction.invoke(adapterPosition)
                        1 -> onDeleteAction.invoke(adapterPosition)
                        else -> throw IllegalArgumentException()
                    }
                }
                return true
            }

            val nameTv = itemView.findViewById<TextView>(R.id.accountNameTv)
            val balanceTv = itemView.findViewById<TextView>(R.id.accountBalanceTv)
            val iconIv = itemView.findViewById<ImageView>(R.id.accountIconIv)
            val newOperationBtn = itemView.findViewById<ImageButton>(R.id.accountNewOperationIb)
            lateinit var accountsFragment: AccountsFragment

            init {
                itemView.setOnCreateContextMenuListener(this)
            }

        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(viewHolder: AccountVH, position: Int) {
            val account = data[position]

            viewHolder.nameTv.text = account.walletName
            viewHolder.balanceTv.text = String.format("%.2f", account.walletValue) + " " + account.currency.symbol
        }
    }
}
