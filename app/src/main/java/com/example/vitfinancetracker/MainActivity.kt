package com.example.vitfinancetracker

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var incomeText: TextView
    private lateinit var spendingText: TextView
    private lateinit var balanceText: TextView
    private lateinit var transactionList: LinearLayout

    private lateinit var addButton: LinearLayout
    private lateinit var transactionsButton: LinearLayout
    private lateinit var homeButton: LinearLayout
    private lateinit var analysisButton: LinearLayout
    private lateinit var settingsButton: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incomeText = findViewById(R.id.incomeText)
        spendingText = findViewById(R.id.spendingText)
        balanceText = findViewById(R.id.balanceText)
        transactionList = findViewById(R.id.transactionList)

        addButton = findViewById(R.id.addButton)
        transactionsButton = findViewById(R.id.transactionsButton)
        homeButton = findViewById(R.id.homeButton)
        analysisButton = findViewById(R.id.analysisButton)
        settingsButton = findViewById(R.id.settingsButton)

        // Bottom navigation
        homeButton.setOnClickListener {
            // Already on Home, do nothing or refresh
        }

        addButton.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }

        transactionsButton.setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
        }

        analysisButton.setOnClickListener {
            // TODO: Add navigation to AnalysisActivity
        }

        settingsButton.setOnClickListener {
            // TODO: Add navigation to SettingsActivity
        }
    }

    override fun onResume() {
        super.onResume()
        updateSummary()
        populateTransactionList()
    }

    private fun updateSummary() {
        val income = 0.0 // Income hardcoded for now

        val spending = TransactionManager.transactions.sumOf { it.amount }
        val balance = income - spending

        incomeText.text = "Income\n₹%.2f".format(income)
        spendingText.text = "Spending\n₹%.2f".format(spending)
        balanceText.text = "Balance : ₹%.2f".format(balance)
    }

    private fun populateTransactionList() {
        transactionList.removeAllViews()
        val inflater = layoutInflater

        for (txn in TransactionManager.transactions) {
            val view = inflater.inflate(R.layout.transaction_itemhome, transactionList, false)
            view.findViewById<TextView>(R.id.text1).text = txn.item
            view.findViewById<TextView>(R.id.text2).text = txn.vendor
            view.findViewById<TextView>(R.id.text3).text = "₹%.2f".format(txn.amount)
            transactionList.addView(view)
        }
    }
}

