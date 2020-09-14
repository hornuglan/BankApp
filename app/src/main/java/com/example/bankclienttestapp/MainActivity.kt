package com.example.bankclienttestapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bankclienttestapp.model.CurrencyRepository
import com.example.bankclienttestapp.model.UserRepository
import com.example.bankclienttestapp.ui.main.viewmodel.MainViewModel
import com.example.bankclienttestapp.ui.main.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navController = Navigation.findNavController(this, R.id.nav_host)


        ViewModelProvider(
            this,
            MainViewModelFactory(
                Application(), UserRepository(),
                CurrencyRepository()
            )
        ).get(MainViewModel::class.java).preloadData()
    }
}
