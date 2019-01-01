package com.example.bankclienttestapp.ui.main

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.*
import com.example.bankclienttestapp.model.Transaction
import com.example.bankclienttestapp.model.UsersRepository
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class MainFragment : Fragment() {

    private lateinit var cardDataLayout: ConstraintLayout

    private lateinit var cardTypeIcon: ImageView
    private lateinit var cardNumber: TextView
    private lateinit var cardHolderName: TextView
    private lateinit var cardExpirationDate: TextView
    private lateinit var cardBalanceCurrentCurrency: TextView
    private lateinit var cardBalanceDefaultCurrency: TextView
    private lateinit var progressbar: ProgressBar

    private lateinit var currencyGbp: RelativeLayout
    private lateinit var currencyGbpIcon: TextView
    private lateinit var currencyGbpCode: TextView

    private lateinit var currencyEur: RelativeLayout
    private lateinit var currencyEurIcon: TextView
    private lateinit var currencyEurCode: TextView

    private lateinit var currencyRub: RelativeLayout
    private lateinit var currencyRubIcon: TextView
    private lateinit var currencyRubCode: TextView

    private lateinit var adapter: TransactionAdapter

    private val viewModel: MainViewModel by activityViewModels()

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
            progressbar = findViewById(R.id.main_fragment_progressbar)

            currencyGbp = findViewById(R.id.currency_gbp)
            currencyGbpIcon = findViewById(R.id.currency_symbol_gbp)
            currencyGbpCode = findViewById(R.id.currency_name_gbp)
            currencyEur = findViewById(R.id.currency_eur)
            currencyEurIcon = findViewById(R.id.currency_symbol_eur)
            currencyEurCode = findViewById(R.id.currency_name_eur)
            currencyRub = findViewById(R.id.currency_rub)
            currencyRubIcon = findViewById(R.id.currency_symbol_rub)
            currencyRubCode = findViewById(R.id.currency_name_rub)
        }

        adapter = TransactionAdapter(LayoutInflater.from(activity), arrayListOf())

        return root
    }

    override fun onStart() {
        super.onStart()

        cardDataLayout.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_cardsFragment)
        }

        currencyGbp.setOnClickListener {
            viewModel.changeCurrency(Currency.GBP)
        }

        currencyEur.setOnClickListener {
            viewModel.changeCurrency(Currency.EUR)
        }

        currencyRub.setOnClickListener {
            viewModel.changeCurrency(Currency.RUB)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViewModel()
        initRecycler()
    }

    private fun initRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.transactions_history_list)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initializeViewModel() {
        viewModel.selectedProfile.observe(viewLifecycleOwner, Observer { it ->
            when(it.type) {
                "mastercard" -> { cardTypeIcon.setImageResource(R.drawable.mastercard) }
                "visa" -> { cardTypeIcon.setImageResource(R.drawable.visa) }
                "unionpay" -> { cardTypeIcon.setImageResource(R.drawable.unionpay) }
            }
            cardNumber.text = it.cardNumber
            cardHolderName.text = it.cardholderName
            cardExpirationDate.text = it.valid
            cardBalanceCurrentCurrency.text = "£ ${it.convertedBalance}"
            cardBalanceDefaultCurrency.text = "$ ${it.balance}"

            currencyGbp.background = resources.getDrawable(R.drawable.rounded_rectangle)
            currencyEur.background = resources.getDrawable(R.drawable.rounded_rectangle)
            currencyRub.background = resources.getDrawable(R.drawable.rounded_rectangle)

            when (it.currency) {
                Currency.GBP -> {
                    currencyGbp.background = resources.getDrawable(R.drawable.rounded_rectangle_blue)
                    cardBalanceCurrentCurrency.text = "£ ${it.convertedBalance}"
                }
                Currency.EUR -> {
                    currencyEur.background = resources.getDrawable(R.drawable.rounded_rectangle_blue)
                    cardBalanceCurrentCurrency.text = "€ ${it.convertedBalance}"
                }
                Currency.RUB -> {
                    currencyRub.background = resources.getDrawable(R.drawable.rounded_rectangle_blue)
                    cardBalanceCurrentCurrency.text = "₽ ${it.convertedBalance}"
                }
                Currency.USD -> {
                    // ignore this case
                }
            }

            adapter.items = it.transactionHistory
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading ->
            if (isLoading) {
                // remove spinner
                progressbar.visibility = View.VISIBLE
            } else {
                if (viewModel.errorMessage.value != null) {
                    Toast.makeText(context, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
                } else {
                    progressbar.visibility = View.GONE
                }
            }
        })
    }
}
