package com.example.bankclienttestapp.ui.main.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.R
import com.example.bankclienttestapp.model.User

class CardsViewHolder(itemView: View, private val isSelectedCard: (user: User) -> Boolean) :
    RecyclerView.ViewHolder(itemView) {
    private val cardType: ImageView = itemView.findViewById(R.id.card_item_type)
    private val cardNumber: TextView = itemView.findViewById(R.id.card_item_number)
    private val selectedCardIcon: ImageView = itemView.findViewById(R.id.card_item_isActive)

    fun bind(item: User) {
        cardNumber.text = item.cardNumber

        when (item.type) {
            "mastercard" -> {
                cardType.setImageResource(R.drawable.mastercard)
            }
            "visa" -> {
                cardType.setImageResource(R.drawable.visa)
            }
            "unionpay" -> {
                cardType.setImageResource(R.drawable.unionpay)
            }
        }

        if (isSelectedCard(item)) {
            selectedCardIcon.visibility = View.VISIBLE
        } else {
            selectedCardIcon.visibility = View.GONE
        }
    }
}