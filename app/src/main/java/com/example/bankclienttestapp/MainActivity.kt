package com.example.bankclienttestapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bankclienttestapp.model.User
import com.example.bankclienttestapp.model.UsersRepository
import com.example.bankclienttestapp.ui.main.MainFragment
import com.example.bankclienttestapp.ui.main.MainViewModel
import com.example.bankclienttestapp.ui.main.MainViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById(R.id.toolbar_main_fragment)
        setActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host)


        ViewModelProvider(
            this,
            MainViewModelFactory(Application(), UsersRepository(), CurrencyRepository())
        ).get(MainViewModel::class.java)
    }
}
