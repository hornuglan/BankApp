package com.example.bankclienttestapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankclienttestapp.model.UsersRepository

class MainViewModelFactory(val application: Application, private val repository: UsersRepository) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application, repository) as T
    }
}
