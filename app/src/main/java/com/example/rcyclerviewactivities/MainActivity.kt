package com.example.rcyclerviewactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

        val fb= findViewById<Button>(R.id.tbFb)
        val signUp= findViewById<Button>(R.id.btSignUp)
        val mobile= findViewById<EditText>(R.id.etMobile)
        val name= findViewById<EditText>(R.id.etName)
        val username= findViewById<EditText>(R.id.etUser)
        val password= findViewById<EditText>(R.id.etPass)
        val checkBox=findViewById<CheckBox>(R.id.checkBox)


        fb.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" ))
            startActivity(browserIntent)
        }
        signUp.setOnClickListener{
            if(mobile.text.isNotEmpty()&&name.text.isNotEmpty()&&username.text.isNotEmpty()&& password.text.isNotEmpty()){

          if(checkBox.isChecked) {
              val intent = Intent(this, Profile::class.java)
              var userdata= arrayListOf<String>(mobile.text.toString(),name.text.toString(),username.text.toString(), password.text.toString())
              intent.putExtra("data",userdata)
              startActivity(intent)
          }
             else
               Toast.makeText(this,"You must agree to terms and conditions",Toast.LENGTH_SHORT).show()

            }else
               Toast.makeText(this,"You must fill all the required information",Toast.LENGTH_SHORT).show()

        }
    }
}