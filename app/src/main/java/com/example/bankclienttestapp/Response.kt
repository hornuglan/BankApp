package com.example.bankclienttestapp

data class Response(
    val users: ArrayList<User>
)

data class User(
    val card_number: String,
    val type: String,
    val cardholder_name: String,
    val valid: String,
    val balance: Double,
    val transaction_history: ArrayList<Transaction>
)

data class Transaction(
    val title: String,
    val icon_url: String,
    val date: String,
    val amount: String
)