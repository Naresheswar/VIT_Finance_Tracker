package com.example.vitfinancetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var itemInput: EditText
    private lateinit var vendorInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var moneyInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var saveButton: Button

    // Sample category list
    private val categories = listOf("Food", "Transport", "Shopping", "Bills", "Health", "Other")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_transaction)

        // Initialize views
        itemInput = findViewById(R.id.itemInput)
        vendorInput = findViewById(R.id.vendorInput)
        categorySpinner = findViewById(R.id.categorySpinner)
        moneyInput = findViewById(R.id.moneyInput)
        dateInput = findViewById(R.id.dateInput)
        saveButton = findViewById(R.id.saveButton)

        // Category dropdown setup
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.adapter = adapter

        // Date picker dialog
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        dateInput.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                calendar.set(y, m, d)
                dateInput.setText(dateFormat.format(calendar.time))
            }, year, month, day)

            datePicker.show()
        }

        // Save button logic
        saveButton.setOnClickListener {
            val item = itemInput.text.toString().trim()
            val vendor = vendorInput.text.toString().trim()
            val category = categorySpinner.selectedItem?.toString() ?: "Other"
            val amount = moneyInput.text.toString().toDoubleOrNull() ?: 0.0
            val date = dateInput.text.toString()

            if (item.isEmpty() || vendor.isEmpty() || amount == 0.0 || date.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val txn = Transaction(item, vendor, category, amount, date)
            TransactionManager.transactions.add(txn)

            Toast.makeText(this, "Transaction Added ✅", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}