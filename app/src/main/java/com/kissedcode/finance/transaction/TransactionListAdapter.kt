package com.kissedcode.finance.transaction

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.databinding.TransactionItemBinding
import com.kissedcode.finance.model.entity.IdleTransaction

class TransactionListAdapter : RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {
    private var list: MutableList<IdleTransaction> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TransactionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.transaction_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updatePostList(postList: MutableList<IdleTransaction>) {
        list = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TransactionViewModel()

        fun bind(post: IdleTransaction) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}
