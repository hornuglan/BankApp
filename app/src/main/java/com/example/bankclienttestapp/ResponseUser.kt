package com.example.bankclienttestapp

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.module.kotlin.KotlinModule

data class Response(
    val users: ArrayList<User>
)
data class User (
//    @get:JsonProperty("card_number")
    val card_number: String,
    val type: String,
//    @JsonProperty("cardholder_name")
    val cardholder_name: String,
    val valid: String,
    val balance: Double,
//    @JsonProperty("transaction_history")
    val transaction_history: ArrayList<Transaction>
)

data class Transaction(
    val title: String,
    val icon_url: String,
    val date: String,
    val amount: String
)