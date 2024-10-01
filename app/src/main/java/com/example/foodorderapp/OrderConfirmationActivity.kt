package com.example.foodorderapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {

    private lateinit var orderListView: ListView
    private lateinit var totalPriceText: TextView
    private lateinit var finishOrderButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        orderListView = findViewById(R.id.orderListView)
        totalPriceText = findViewById(R.id.totalPriceText)
        finishOrderButton = findViewById(R.id.finishOrderButton)

        // Get the selected dishes from the Intent
        val selectedDishIndices = intent.getIntegerArrayListExtra("selectedDishes")
        val dishNames = intent.getStringArrayListExtra("dishNames")
        val dishPrices = intent.getDoubleArrayExtra("dishPrices")

        val selectedItems = selectedDishIndices?.map { index ->
            "${dishNames?.get(index)} - \$${dishPrices?.get(index)}"
        }

        // Display the selected dishes
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            selectedItems.orEmpty()
        )
        orderListView.adapter = adapter

        // Calculate and display the total price
        val totalPrice = selectedDishIndices?.sumOf { dishPrices?.get(it) ?: 0.0 } ?: 0.0
        totalPriceText.text = "Total Price: \$${"%.2f".format(totalPrice)}"

        // Handle the Finish Order button click
        finishOrderButton.setOnClickListener {
            // Show a success message and close the activity
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
            finish()  // Close the activity and return to the menu
        }
    }
}
