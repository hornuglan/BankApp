package com.example.bankclienttestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.model.Transaction

class TransactionAdapter(
    private val inflater: LayoutInflater,
    var items: ArrayList<Transaction>
) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            inflater.inflate(
                R.layout.layout_transactions_history_list_item,
                parent,
                false
            )
        )
    }
}