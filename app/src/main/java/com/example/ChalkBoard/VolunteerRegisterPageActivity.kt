package com.example.ChalkBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ChalkBoard.databinding.ActivityVolunteerRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class VolunteerRegisterPageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityVolunteerRegisterPageBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if(binding.etPhoneNumber.text!!.isEmpty()){
                Toast.makeText(this, "Please enter your Number!!", Toast.LENGTH_SHORT).show()
            }else{
                var intent = Intent(this, OtpVerficationActivity::class.java)
                intent.putExtra("PhoneNumber", binding.etPhoneNumber.text!!.toString())
                startActivity(intent)
            }
        }
    }
}