package com.example.bankclienttestapp

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class Response(
    val users: ArrayList<User>
)

@Parcelize
data class User (
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