package com.example.bankclienttestapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCurrency(
    @SerializedName("Date")
    val date: String,
    @SerializedName("PreviousDate")
    val previousDate: String,
    @SerializedName("PreviousURL")
    val previousUrl: String,
    @SerializedName("Timestamp")
    val timestamp: String,
    @SerializedName("Valute")
    val valute: ArrayList<Valute>
): Parcelable

@Parcelize
data class Valute(
    @SerializedName("ID")
    val id: String,
    @SerializedName("NumCode")
    val numCode: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: Double,
    @SerializedName("Previous")
    val previous: Double
): Parcelable