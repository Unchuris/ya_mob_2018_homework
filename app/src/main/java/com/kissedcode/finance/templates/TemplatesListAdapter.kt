package com.kissedcode.finance.templates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.model.entity.IdleTransaction
import kotlinx.android.synthetic.main.templates_item.view.btnApply
import kotlinx.android.synthetic.main.templates_item.view.btnDeleteTemplate
import kotlinx.android.synthetic.main.templates_item.view.tvAmount
import kotlinx.android.synthetic.main.templates_item.view.tvCategoryName

class TemplatesListAdapter(private val callback: RecycleOnClickListenerCallback) : RecyclerView.Adapter<TemplatesListAdapter.ViewHolder>() {

    var list: MutableList<IdleTransaction> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.templates_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.btnDeleteTemplate.setOnClickListener {
            callback(viewHolder.adapterPosition, "onTemplateDelete")
        }
        view.btnApply.setOnClickListener {
            callback(viewHolder.adapterPosition, "onApplyTemplate")
        }
        view.setOnClickListener {
            callback(viewHolder.adapterPosition, "openFragment")
        }
        return viewHolder
    }

    private fun callback(position: Int, action: String) {
        if (position != RecyclerView.NO_POSITION) {
            when (action) {
                "openFragment" -> {
                    callback.openFragment(list[position])
                }
                "onApplyTemplate" -> {
                    callback.onApplyTemplate(list[position])
                }
                "onTemplateDelete" -> {
                    callback.onTemplateDelete(list[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(post: IdleTransaction) {
            itemView.tvAmount.text = String.format("%.2f", post.idleTransactionAmount) + " " + post.currency.symbol
            itemView.tvCategoryName.text = post.category.categoryName
            val color = when (post.category.categoryType) {
                OperationType.INCOME -> R.color.red
                OperationType.SPEND -> R.color.green
            }
            itemView.tvCategoryName.setTextColor(color)
        }
    }

    interface RecycleOnClickListenerCallback {
        fun onTemplateDelete(transaction: IdleTransaction)
        fun onApplyTemplate(transaction: IdleTransaction)
        fun openFragment(transaction: IdleTransaction)
    }
}