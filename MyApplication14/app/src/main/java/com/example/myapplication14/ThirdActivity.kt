package com.example.myapplication14

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
    }

    fun prevSlide2(view: View)
    {
        val randomIntent= Intent(this,SecondActivity::class.java)
        startActivity(randomIntent)
    }
}
