package com.example.vitfinancetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class TransactionItemSimple(val item: String, val vendor: String, val amount: String)

class TransactionAdapterSimple(private val items: List<TransactionItemSimple>) :
    RecyclerView.Adapter<TransactionAdapterSimple.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.text1)
        val vendorText: TextView = view.findViewById(R.id.text2)
        val amountText: TextView = view.findViewById(R.id.text3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_itemhome, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val txn = items[position]
        holder.itemText.text = txn.item
        holder.vendorText.text = txn.vendor
        holder.amountText.text = txn.amount
    }

    override fun getItemCount() = items.size
}