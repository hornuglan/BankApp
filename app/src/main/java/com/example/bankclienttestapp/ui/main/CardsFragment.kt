package com.example.bankclienttestapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.CardsAdapter
import com.example.bankclienttestapp.MainActivity
import com.example.bankclienttestapp.R
import com.example.bankclienttestapp.model.User

class CardsFragment : Fragment() {
    private lateinit var adapter: CardsAdapter

    private lateinit var backButton: ImageView

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.cards_fragment,
            container,
            false
        )

        with(root) {
            backButton = findViewById(R.id.back_button_cards_fragment)
        }

        val callback = { user: User ->
            viewModel.selectUser(user)
            (activity as MainActivity).navController.navigate(R.id.action_cardsFragment_to_mainFragment)
        }

        adapter = CardsAdapter(LayoutInflater.from(activity), arrayListOf(), callback, viewModel::isSelectedCard)

        return root
    }

    override fun onStart() {
        super.onStart()
        backButton.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_cardsFragment_to_mainFragment)
        }

        // 1. click on recycler
        // get item ID
        // viewModel.setCurrentProfileTo(ie 1)
        // viewModel.userProfiles
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViewModel()
        initRecycler()
    }

    private fun initRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.card_fragment_list)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initializeViewModel() {
        viewModel.userProfiles.observe(viewLifecycleOwner, Observer { it ->
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }

    interface SelectUserListener {
        fun selectUser(user: User)
    }

    interface IsCardSelectedListener {
        fun isSelectedCard(user: User) : Boolean
    }
}