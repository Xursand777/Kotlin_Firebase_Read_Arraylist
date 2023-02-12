package com.x7.kotlin_firebase_read_arraylist.view

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.x7.kotlin_firebase_read_arraylist.R
import com.x7.kotlin_firebase_read_arraylist.databinding.RecyclerviewItemBinding

import com.x7.kotlin_firebase_read_arraylist.model.User
import com.x7.kotlin_firebase_read_arraylist.model.room.Person

class PersonAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<Person>

):RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {


    val  databaseReference= FirebaseDatabase.getInstance().getReference().child("Users")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {


        holder.binding.textviewuser.text=arrayList.get(position).name
        holder.binding.textviewphone.text=arrayList.get(position).phone


    }
    override fun getItemCount(): Int =arrayList.size

    class PersonViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)

}