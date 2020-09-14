package com.example.bankclienttestapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("card_number")
    val cardNumber: String,
    val type: String,
    @SerializedName("cardholder_name")
    val cardholderName: String,
    val valid: String,
    val balance: Double,
    @SerializedName("transaction_history")
    val transactionHistory: ArrayList<Transaction>
) : Parcelable

@Parcelize
data class Transaction(
    val title: String,
    @SerializedName("icon_url")
    val iconUrl: String,
    val date: String,
    val amount: String
) : Parcelable