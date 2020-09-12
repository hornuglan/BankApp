package com.example.bankclienttestapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankclienttestapp.model.User

class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardType: ImageView = itemView.findViewById(R.id.card_item_type)
    private val cardNumber: TextView = itemView.findViewById(R.id.card_item_number)

    fun bind(item: User) {
       cardNumber.text = item.cardNumber

        Glide.with(cardType)
            .load(item.type)
            .centerCrop()
            .into(cardType)
    }
}