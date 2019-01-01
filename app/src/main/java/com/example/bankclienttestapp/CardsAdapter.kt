package com.example.bankclienttestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.model.User

class CardsAdapter(
    private val inflater: LayoutInflater,
    var items: ArrayList<User>,
    private val selectNewProfile: (user: User) -> Unit,
    private val isSelectedCard: (user: User) -> Boolean
) : RecyclerView.Adapter<CardsViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val cardNumber = holder.itemView.findViewById<TextView>(R.id.card_item_number)
        cardNumber.setOnClickListener {
            selectNewProfile.invoke(items[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(
            inflater.inflate(
                R.layout.cards_fragment_list_item,
                parent,
                false
            ),
            isSelectedCard
        )
    }
}