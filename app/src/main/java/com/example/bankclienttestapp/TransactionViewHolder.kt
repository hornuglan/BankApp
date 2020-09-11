package com.example.bankclienttestapp

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val transactionIcon:ImageView = itemView.findViewById(R.id.transaction_item_icon)
    private val transactionTitle: TextView = itemView.findViewById(R.id.transaction_item_title)
    private val transactionDate: TextView = itemView.findViewById(R.id.transaction_item_date)
    private val transactionCurrentSum: TextView = itemView.findViewById(R.id.transaction_item_current_sum)
    private val transactionDefaultSum: TextView = itemView.findViewById(R.id.transaction_item_default_sum)

    fun bind(item: Transaction) {
        transactionTitle.text = item.title
        transactionDate.text = item.date
        transactionCurrentSum.text = item.amount
        transactionDefaultSum.text = item.amount

        Glide.with(transactionIcon)
            .load(item.icon_url)
            .centerCrop()
            .into(transactionIcon)
    }
}