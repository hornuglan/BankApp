package com.example.bankclienttestapp.ui.main

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankclienttestapp.Currency
import com.example.bankclienttestapp.CurrencyRepository
import com.example.bankclienttestapp.model.User
import com.example.bankclienttestapp.model.UsersRepository
import com.example.bankclienttestapp.model.Valute
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserProfile(
    val cardNumber: String,
    val type: String,
    val cardholderName: String,
    val valid: String,
    val balance: Double,
    val convertedBalance: Double,
    val currency: Currency,
    val transactionHistory: ArrayList<Transaction>
)

data class Transaction(
    val title: String,
    val iconUrl: String,
    val date: String,
    val amount: String,
    val convertedAmount: Double,
    val currency: Currency
)

class MainViewModel(
    private val application: Application,
    private val repository: UsersRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String?>()

    val userProfiles = MutableLiveData<ArrayList<User>>()
    val selectedProfile = MutableLiveData<UserProfile>()
    private val currency = MutableLiveData<Currency>()
    private val currencyRates = MutableLiveData<ArrayList<Valute>>()

    fun preloadData() {
        isLoading.value = true
        errorMessage.value = null
        // load currency rates
        // success -> load user
        // success -> isLoading = false, save currency rates, save user profiles
        // failure -> errorMessage.postValue = "", isLoading = false
        // failure -> errorMessage.postValue = "", isLoading = false

//        currencyRepository.loadCurrencies({
//            repository.loadUserProfiles({responseUser ->
//                currencyRates.postValue(it.valute)
//                userProfiles.postValue(responseUser.users)
//
//                val user = responseUser.users[0]
//                selectedProfile.postValue(createUserProfile(user))
//            }, {
//                errorMessage.postValue(it)
//            })
//        }, {
//            errorMessage.postValue(it)
//        })

        repository.loadUserProfiles({responseUser ->
            userProfiles.postValue(responseUser.users)

            val user = responseUser.users[2]
            selectedProfile.postValue(createUserProfile(user))
        }, {
            errorMessage.postValue(it)
        })
    }

    private fun createUserProfile(user: User): UserProfile {
        val transactions = user.transactionHistory.map {
            Transaction(
                title = it.title,
                iconUrl = it.iconUrl,
                date = it.date,
                amount = it.amount,
                convertedAmount = it.amount.toDouble() * 0.5,
                currency = Currency.EUR
            )
        }
        return UserProfile(
            cardNumber = user.cardNumber,
            type = user.type,
            cardholderName = user.cardholderName,
            valid = user.valid,
            balance = user.balance,
            convertedBalance = user.balance * 0.5,
            currency = Currency.EUR,
            transactionHistory = transactions.toCollection(arrayListOf())
        )
    }

    private fun convertCurrency(usd: Double, targetCurrency: Double) {
        //get usd valute.value
        //1 rub / valute.value
//        currencyRates.value.find { it.}

        val rate = 1 / usd
    }
}
