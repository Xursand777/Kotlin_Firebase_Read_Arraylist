package com.x7.kotlin_firebase_read_arraylist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.x7.kotlin_firebase_read_arraylist.databinding.ActivityMain2Binding
import com.x7.kotlin_firebase_read_arraylist.model.User

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users")


        binding.apply {

            binding.buttonadd.setOnClickListener {
                var pushkey=databaseReference.push().key.toString()
                val user= User(edittextusername.text.toString(),edittextphone.text.toString(),pushkey)
                databaseReference.child(pushkey).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){
                        edittextusername.text.clear()
                        edittextphone.text.clear()
                    }
                }

            }

        }

    }


}