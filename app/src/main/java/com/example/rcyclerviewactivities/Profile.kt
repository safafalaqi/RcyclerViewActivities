package com.example.rcyclerviewactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val array=intent.getStringArrayListExtra("data")

        findViewById<TextView>(R.id.tvProName).setText(array?.get(1))
        findViewById<TextView>(R.id.tvUserName).setText(array?.get(2))
        findViewById<TextView>(R.id.tvEmailPro).setText(array?.get(0))

        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainActivity-> startActivity(Intent(this,MainActivity::class.java))
                R.id.recyclerview-> startActivity(Intent(this,RecyclerViewActivity::class.java))
                R.id.profile-> startActivity(Intent(this,Profile::class.java))
            }
            true
        }
    }
}