package com.example.vitfinancetracker

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransactionsActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var transactionsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        searchInput = findViewById(R.id.searchInput)
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView)

        transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        transactionsRecyclerView.adapter = TransactionAdapterCard(TransactionManager.transactions)
    }

    override fun onResume() {
        super.onResume()
        transactionsRecyclerView.adapter?.notifyDataSetChanged()
    }
}