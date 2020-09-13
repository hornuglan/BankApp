package com.example.bankclienttestapp.ui.main

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankclienttestapp.Currency
import com.example.bankclienttestapp.CurrencyRepository
import com.example.bankclienttestapp.MainActivity
import com.example.bankclienttestapp.R
import com.example.bankclienttestapp.model.User
import com.example.bankclienttestapp.model.UsersRepository
import com.example.bankclienttestapp.model.Valute
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.RoundingMode
import kotlin.math.round

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
) : ViewModel(), CardsFragment.SelectUserListener {
    val isLoading = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String?>()

    val userProfiles = MutableLiveData<ArrayList<User>>()
    val selectedProfile = MutableLiveData<UserProfile>()
    val selectedCurrency = MutableLiveData<Currency>()
    private val currencyRates = MutableLiveData<HashMap<String, Valute>>()

    fun preloadData() {
        isLoading.value = true
        errorMessage.value = null

        currencyRepository.loadCurrencies({
            repository.loadUserProfiles({responseUser ->
                currencyRates.postValue(it.valute)
                userProfiles.postValue(responseUser.users)
                selectedCurrency.postValue(Currency.GBP)

                val user = responseUser.users[0]
                selectedProfile.postValue(createUserProfile(user, Currency.USD))
            }, {
                isLoading.postValue(false)
                errorMessage.postValue(it)
            })
        }, {
            isLoading.postValue(false)
            errorMessage.postValue(it)
        })
    }

    fun changeCurrency(currency: Currency) {
        selectedProfile.value.let {
            val newProfile = createUserProfile(userProfileToUser(it!!), currency)
            selectedProfile.value = newProfile
            selectedCurrency.value = currency
        }
    }

    private fun createUserProfile(user: User, currency: Currency): UserProfile {
        val transactions = user.transactionHistory.map {
            Transaction(
                title = it.title,
                iconUrl = it.iconUrl,
                date = it.date,
                amount = it.amount,
                convertedAmount = convertCurrency(it.amount.toDouble(), currency),
                currency = currency
            )
        }
        return UserProfile(
            cardNumber = user.cardNumber,
            type = user.type,
            cardholderName = user.cardholderName,
            valid = user.valid,
            balance = user.balance,
            convertedBalance = convertCurrency(user.balance.toDouble(), currency),
            currency = currency,
            transactionHistory = transactions.toCollection(arrayListOf())
        )
    }

    private fun userProfileToUser(userProfile: UserProfile): User {
        val transactions = userProfile.transactionHistory.map {
            com.example.bankclienttestapp.model.Transaction(
                title = it.title,
                iconUrl = it.iconUrl,
                date = it.date,
                amount = it.amount
            )
        }
        return User(
            cardNumber = userProfile.cardNumber,
            type = userProfile.type,
            cardholderName = userProfile.cardholderName,
            valid = userProfile.valid,
            balance = userProfile.balance,
            transactionHistory = transactions.toCollection(arrayListOf())
        )
    }

    private fun convertCurrency(usd: Double, targetCurrency: Currency): Double {
        //get usd valute.value
        //1 rub / valute.value
//        currencyRates.value.find { it.}

        var currencyCode = "USD"
        if (targetCurrency == Currency.GBP) { currencyCode = "GBP" }
        if (targetCurrency == Currency.USD) { currencyCode = "USD" }
        if (targetCurrency == Currency.EUR) { currencyCode = "EUR" }
        if (targetCurrency == Currency.RUB) { currencyCode = "RUB" }


        val currencyRates = currencyRates.value!!

        if (currencyCode == "RUB") {
            val rate = currencyRates["USD"]!!
            return (usd * rate.value).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        }

        if (currencyCode == "USD") {
            return usd
        }

        if (currencyCode == "GBP") {
            val rate = currencyRates["USD"]!!
            val rubles = usd * rate.value
            val gbpRate = currencyRates["GBP"]!!
            return (rubles / gbpRate.value).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        }

        if (currencyCode == "EUR") {
            val rate = currencyRates["USD"]!!
            val rubles = usd * rate.value
            val eurRate = currencyRates["EUR"]!!
            return (rubles / eurRate.value).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        }

        return usd
    }

    override fun selectUser(user: User) {
       val currency = selectedCurrency.value!!

        val newProfile = createUserProfile(user, currency)
        selectedProfile.postValue(newProfile)
    }
}