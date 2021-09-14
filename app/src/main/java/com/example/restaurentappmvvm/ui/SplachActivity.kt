package com.example.restaurentappmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.restaurentappmvvm.R
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ChasingDots
import kotlinx.android.synthetic.main.activity_splach.*

class SplachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)


        btnGetStarted.setOnClickListener {
            btnGetStarted.visibility = View.INVISIBLE
            loader_splach.visibility = View.VISIBLE
            val intent = Intent(this@SplachActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}