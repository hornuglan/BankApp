package com.example.bankclienttestapp.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankclienttestapp.CardsAdapter
import com.example.bankclienttestapp.CurrencyRepository
import com.example.bankclienttestapp.R
import com.example.bankclienttestapp.model.UsersRepository
import com.google.android.material.appbar.MaterialToolbar

class CardsFragment : Fragment() {
    private lateinit var toolbar: Toolbar

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: CardsAdapter

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

        adapter = CardsAdapter(LayoutInflater.from(activity), arrayListOf())

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViewModel()

        viewModel.preloadData()
        initRecycler()
    }

    private fun initRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.card_fragment_list)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initializeViewModel() {
        viewModel = activity.let {
            ViewModelProvider(this, MainViewModelFactory(Application(),  UsersRepository(), CurrencyRepository())).get(MainViewModel::class.java)
        }

        viewModel.userProfiles.observe(viewLifecycleOwner, Observer { it ->
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }
}