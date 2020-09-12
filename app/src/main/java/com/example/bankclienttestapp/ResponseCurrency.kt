package com.example.bankclienttestapp

import com.fasterxml.jackson.annotation.JsonCreator

data class ResponseCurrency(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    val Valute: ArrayList<Valute>
)

data class Valute(
    val ID: String,
    val NumCode: String,
    val CharCode: String,
    val Nominal: Int,
    val Name: String,
    val Value: Double,
    val Previous: Double
)