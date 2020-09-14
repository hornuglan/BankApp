package com.example.bankclienttestapp.model

import android.os.Parcelable
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyResponse(
    @SerializedName("Date")
    val date: String,
    @SerializedName("PreviousDate")
    val previousDate: String,
    @SerializedName("PreviousURL")
    val previousUrl: String,
    @SerializedName("Timestamp")
    val timestamp: String,
    @SerializedName("Valute")
    val valute: HashMap<String, Valute>
) : Parcelable

class CurrencyRepository {
    fun loadCurrencies(onSuccess: (CurrencyResponse) -> Unit, onFailure: (String) -> Unit) {
        val url = "https://www.cbr-xml-daily.ru/daily_json.js"

        Fuel.get(url)
            .header(Headers.CONTENT_TYPE, "application/json")
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val e = result.getException()
                        Log.d("Request Error", "${e.message}")
                        onFailure("${e.message}")

                    }
                    is Result.Success -> {
                        val currencies = Gson().fromJson(result.get(), CurrencyResponse::class.java)
                        onSuccess(currencies)
                    }
                }
            }
    }
}