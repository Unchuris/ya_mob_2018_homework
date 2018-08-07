package com.kissedcode.finance.templates

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.databinding.TemplatesItemBinding
import com.kissedcode.finance.model.entity.IdleTransaction

class TemplatesListAdapter(private val callback: RecycleOnClickListenerCallback) : RecyclerView.Adapter<TemplatesListAdapter.ViewHolder>() {

    private var list: MutableList<IdleTransaction> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TemplatesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.templates_item, parent, false)
        val viewHolder = ViewHolder(binding)
        binding.btnDeleteTemplate.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                callback.onTemplateDelete(list[position])
            }
        }
        binding.btnApply.setOnClickListener{
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                callback.onApplyTemplate(list[position])
            }
        }
        return viewHolder
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

    class ViewHolder(private val binding: TemplatesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TemplatesViewModel()

        fun bind(post: IdleTransaction) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }

    interface RecycleOnClickListenerCallback {
        fun onTemplateDelete(transaction: IdleTransaction)
        fun onApplyTemplate(transaction: IdleTransaction)
    }
}