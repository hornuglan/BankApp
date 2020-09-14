package com.example.bankclienttestapp.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankclienttestapp.model.CurrencyRepository
import com.example.bankclienttestapp.model.UserRepository

class MainViewModelFactory(
    private val application: Application,
    private val repository: UserRepository,
    private val currencyRepository: CurrencyRepository
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            application,
            repository,
            currencyRepository
        ) as T
    }
}
