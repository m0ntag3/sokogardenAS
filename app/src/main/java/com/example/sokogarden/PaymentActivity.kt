package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Find the views by their ids
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val txtcost = findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val txtdescription = findViewById<TextView>(R.id.txtProductDescription)

        // Retrieve the data first from the previous activity(MainActivity)
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")
        val description = intent.getStringExtra("product_description")

        // Update the TextView with the data from the previous activity
        txtname.text = name
        txtcost.text = "Ksh $cost"
        txtdescription.text = description

        // Specify the image url ($)is added to enable displaying/ viewing of the image
        val imageUrl = "https://collinspaul.alwaysdata.net/static/images/$product_photo"

        // Pasted from Product.kt to assist with product image viewing
        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)

        // Find the Edit text and the "Pay Now" button using their ids
        val phone = findViewById<EditText>(R.id.phone)
        val btnPay= findViewById<Button>(R.id.pay)

        // setonClick listener
        btnPay.setOnClickListener {

            // Specify the api for making payment
            val api = "https://collinspaul.alwaysdata.net/api/mpesa_payment"

            // Create a RequestParams
            val data = RequestParams()

            // insert data into the RequestParams
                // In this case we use the price imported from database(product_cost) and given the name cost
            data.put("amount", cost)
            data.put("phone", phone.text.toString().trim())
            data.put("description", description)

            // import the helper class
            val helper = ApiHelper(applicationContext)

            // Access the post function in the Helper class
            helper.post(api,data)

            // Clear the phone number from EditText
            phone.text.clear()
        }

    }
}