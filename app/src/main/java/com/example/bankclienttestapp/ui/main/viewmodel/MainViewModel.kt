package com.example.bankclienttestapp.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankclienttestapp.model.Currency
import com.example.bankclienttestapp.model.CurrencyRepository
import com.example.bankclienttestapp.model.User
import com.example.bankclienttestapp.model.UserRepository
import com.example.bankclienttestapp.model.Valute
import com.example.bankclienttestapp.ui.main.fragments.CardsFragment
import java.math.RoundingMode

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
    private val repository: UserRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel(),
    CardsFragment.SelectUserListener,
    CardsFragment.IsCardSelectedListener {

    val isLoading = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String?>()

    val userProfiles = MutableLiveData<ArrayList<User>>()
    val selectedProfile = MutableLiveData<UserProfile>()

    private val selectedCurrency = MutableLiveData<Currency>()
    private val currencyRates = MutableLiveData<HashMap<String, Valute>>()

    fun preloadData() {
        isLoading.value = true
        errorMessage.value = null

        currencyRepository.loadCurrencies({
            repository.loadUserProfiles({ responseUser ->
                currencyRates.postValue(it.valute)
                userProfiles.postValue(responseUser.users)
                selectedCurrency.postValue(Currency.GBP)

                val user = responseUser.users[0]
                selectedProfile.postValue(userToUserProfile(user, Currency.GBP))
                isLoading.postValue(false)
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
        val newProfile = userToUserProfile(userProfileToUser(selectedProfile.value!!), currency)
        selectedProfile.value = newProfile
        selectedCurrency.value = currency
    }

    private fun userToUserProfile(user: User, currency: Currency): UserProfile {
        return UserProfile(
            cardNumber = user.cardNumber,
            type = user.type,
            cardholderName = user.cardholderName,
            valid = user.valid,
            balance = user.balance,
            convertedBalance = convertCurrency(user.balance.toDouble(), currency),
            currency = currency,
            transactionHistory = user.transactionHistory.map {
                Transaction(
                    title = it.title,
                    iconUrl = it.iconUrl,
                    date = it.date,
                    amount = it.amount,
                    convertedAmount = convertCurrency(it.amount.toDouble(), currency),
                    currency = currency
                )
            }.toCollection(arrayListOf())
        )
    }

    private fun userProfileToUser(userProfile: UserProfile): User {
        return User(
            cardNumber = userProfile.cardNumber,
            type = userProfile.type,
            cardholderName = userProfile.cardholderName,
            valid = userProfile.valid,
            balance = userProfile.balance,
            transactionHistory = userProfile.transactionHistory.map {
                com.example.bankclienttestapp.model.Transaction(
                    title = it.title,
                    iconUrl = it.iconUrl,
                    date = it.date,
                    amount = it.amount
                )
            }.toCollection(arrayListOf())
        )
    }

    private fun convertCurrency(usd: Double, targetCurrency: Currency): Double {
        val currencyRates = currencyRates.value!!

        return when(targetCurrency) {
            Currency.RUB -> {
                val rate = currencyRates["USD"]!!
                (usd * rate.value).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            }
            Currency.USD -> {
                usd
            }
            Currency.GBP, Currency.EUR -> {
                val rate = currencyRates["USD"]!!
                val rubles = usd * rate.value
                val targetRate = currencyRates[targetCurrency.toString()]!!
                (rubles / targetRate.value).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            }
        }
    }

    override fun selectUser(user: User) {
        val currency = selectedCurrency.value!!

        selectedProfile.postValue(userToUserProfile(user, currency))
    }

    override fun isSelectedCard(user: User): Boolean {
        return selectedProfile.value?.cardNumber == user.cardNumber
    }
}