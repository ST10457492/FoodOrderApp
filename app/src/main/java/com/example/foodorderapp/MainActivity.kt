package com.example.foodorderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var menuListView: ListView
    private lateinit var confirmOrderButton: Button

    // Sample data: Dish names, descriptions, courses, and prices
    private val dishNames = arrayOf(
        "Caesar Salad", "Tomato Soup", "Steak", "Chicken Curry", "Chocolate Cake", "Ice Cream"
    )
    private val dishCourses = arrayOf(
        "Starter", "Starter", "Main Course", "Main Course", "Dessert", "Dessert"
    )
    private val dishPrices = arrayOf(
        5.99, 4.99, 18.99, 12.99, 6.99, 4.50
    )

    private val selectedDishes = mutableListOf<Int>()  // Stores indices of selected dishes

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ListView and Button
        menuListView = findViewById(R.id.menuListView)
        confirmOrderButton = findViewById(R.id.confirmOrderButton)

        // Create a list of dish details to show in ListView
        val menuItems = dishNames.mapIndexed { index, name ->
            "$name - ${dishCourses[index]} - \$${dishPrices[index]}"
        }

        // Set up the ListView with multiple choice items
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            menuItems
        )
        menuListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        menuListView.adapter = adapter

        // Handle Confirm Order button click
        confirmOrderButton.setOnClickListener {
            selectedDishes.clear()
            for (i in 0 until menuListView.count) {
                if (menuListView.isItemChecked(i)) {
                    selectedDishes.add(i)
                }
            }

            if (selectedDishes.isNotEmpty()) {
                // Pass the selected dish indices and details to OrderConfirmationActivity
                val intent = Intent(this, OrderConfirmationActivity::class.java)
                intent.putIntegerArrayListExtra("selectedDishes", ArrayList(selectedDishes))
                intent.putStringArrayListExtra("dishNames", ArrayList(dishNames.asList()))

                // Use putExtra for double array
                intent.putExtra("dishPrices", dishPrices) // This line fixes the issue
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select at least one dish.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}