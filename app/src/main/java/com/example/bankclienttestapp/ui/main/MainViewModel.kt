package com.example.bankclienttestapp.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankclienttestapp.Currency
import com.example.bankclienttestapp.model.User
import com.example.bankclienttestapp.model.UsersRepository
import com.example.bankclienttestapp.model.Valute

class MainViewModel(private val application: Application, private val repository: UsersRepository) : ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String?>()

    val userProfiles = MutableLiveData<ArrayList<User>>()
    val selectedProfile = MutableLiveData<User>()
    private val currency = MutableLiveData<Currency>()
    private val currencyRates = MutableLiveData<ArrayList<Valute>>()
    private val convertedProfile = MutableLiveData<User>()

    fun preloadData() {
        isLoading.value = true
        errorMessage.value = null
        // load currency rates
        // success -> load user
                     // success -> isLoading = false, save currency rates, save user profiles
                    // failure -> errorMessage.postValue = "", isLoading = false
        // failure -> errorMessage.postValue = "", isLoading = false
        repository.loadUserProfiles({
            userProfiles.postValue(it.users)
            selectedProfile.postValue(it.users[0])
        },{
            errorMessage.postValue(it)
        })
    }
}
