package com.example.bankclienttestapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson

class MainFragment : Fragment() {

    private lateinit var cardDataLayout: ConstraintLayout

    private lateinit var cardTypeIcon: ImageView
    private lateinit var cardNumber: TextView
    private lateinit var cardHolderName: TextView
    private lateinit var cardExpirationDate: TextView
    private lateinit var cardBalanceCurrentCurrency: TextView
    private lateinit var cardBalanceDefaultCurrency: TextView

    private lateinit var adapter: TransactionAdapter

    private var transactions = arrayListOf<Transaction>()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        with(root) {
            cardDataLayout = findViewById(R.id.card_data)
            cardTypeIcon = findViewById(R.id.card_type_icon)
            cardNumber = findViewById(R.id.card_number)
            cardHolderName = findViewById(R.id.card_holder_name)
            cardExpirationDate = findViewById(R.id.card_validation_date)
            cardBalanceCurrentCurrency = findViewById(R.id.card_current_currency)
            cardBalanceDefaultCurrency = findViewById(R.id.card_default_currency)
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        cardDataLayout.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_cardsFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        getCardData()
        initRecycler()
    }

    private fun getCardData() {
        val url = "https://hr.peterpartner.net/test/android/v1/users.json"

        Fuel.get(url)
            .header(Headers.CONTENT_TYPE, "application/json")
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val e = result.getException()
                        Log.d("Request Error", "${e.message}")
                    }
                    is Result.Success -> {
                        val users = Gson().fromJson(result.get(), Response::class.java)
                        val user = users.users[0]

                        activity?.runOnUiThread {
                            when(user.type) {
                                "mastercard" -> { cardTypeIcon.setImageResource(R.drawable.mastercard) }
                                "visa" -> { cardTypeIcon.setImageResource(R.drawable.visa) }
                                "unionpay" -> { cardTypeIcon.setImageResource(R.drawable.unionpay) }
                            }
                            cardNumber.text = user.cardNumber
                            cardHolderName.text = user.cardholderName
                            cardExpirationDate.text = user.valid
                            cardBalanceCurrentCurrency.text = "£ ${user.balance}"
                            cardBalanceDefaultCurrency.text = "$ ${user.balance}"
                        }

                        val transactions = users.users[0].transactionHistory
                        transactions.forEach {
                            transactions.add(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    private fun initRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.transactions_history_list)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = TransactionAdapter(
            LayoutInflater.from(activity),
            transactions
        )
    }

}
