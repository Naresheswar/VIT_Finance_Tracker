package com.example.vitfinancetracker

data class Transaction(
    val item: String,
    val vendor: String,
    val category: String,
    val amount: Double,
    val date: String
)