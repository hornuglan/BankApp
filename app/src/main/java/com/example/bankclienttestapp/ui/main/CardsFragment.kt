package com.example.bankclienttestapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bankclienttestapp.R
import com.google.android.material.appbar.MaterialToolbar

class CardsFragment : Fragment() {
    private lateinit var toolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.cards_fragment, container, false)

        with(root) {
            toolbar = findViewById(R.id.toolbar_cards_fragment)
        }

        return root
    }
}