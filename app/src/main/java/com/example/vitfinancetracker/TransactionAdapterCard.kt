package com.example.vitfinancetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapterCard(private val items: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapterCard.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.tvItemName)
        val vendor: TextView = view.findViewById(R.id.tvVendor)
        val category: TextView = view.findViewById(R.id.tvCategory)
        val date: TextView = view.findViewById(R.id.tvDate)
        val amount: TextView = view.findViewById(R.id.tvAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val txn = items[position]
        holder.itemName.text = txn.item
        holder.vendor.text = txn.vendor
        holder.category.text = "Category: ${txn.category}"
        holder.date.text = txn.date
        holder.amount.text = "₹%.2f".format(txn.amount)
    }

    override fun getItemCount() = items.size
}