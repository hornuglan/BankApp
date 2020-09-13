package com.example.bankclienttestapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankclienttestapp.CurrencyRepository
import com.example.bankclienttestapp.model.UsersRepository

class MainViewModelFactory(
    private val application: Application,
    private val repository: UsersRepository,
    private val currencyRepository: CurrencyRepository
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application, repository, currencyRepository) as T
    }
}
