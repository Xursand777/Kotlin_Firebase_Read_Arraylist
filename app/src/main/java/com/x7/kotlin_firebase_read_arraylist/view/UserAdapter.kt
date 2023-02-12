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

class UserAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<User>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    val  databaseReference= FirebaseDatabase.getInstance().getReference().child("Users")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (arrayList.get(position).selected){
            holder.binding.relativelayyy.visibility=View.VISIBLE

        }else{
            holder.binding.relativelayyy.visibility=View.INVISIBLE

        }

        holder.binding.textviewuser.text=arrayList.get(position).name
        holder.binding.textviewphone.text=arrayList.get(position).phone

        holder.binding.relativelayout1.setOnLongClickListener {
            holder.binding.apply {
               arrayList.get(position).selected=!arrayList.get(position).selected
                if (arrayList.get(position).selected){
                    holder.binding.relativelayyy.visibility=View.VISIBLE

                }else{
                    holder.binding.relativelayyy.visibility=View.INVISIBLE

                }

            }


            return@setOnLongClickListener true
        }
        holder.binding.imageviewdelete.setOnClickListener {
            val alertDialog=AlertDialog.Builder(context)
            alertDialog.setTitle("Delete")
            alertDialog.setMessage("${arrayList.get(position).name} delete this user?")
            alertDialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    databaseReference.child(arrayList.get(position).pushkey).removeValue()
                }

            })
            alertDialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }

            })
            alertDialog.create().show()
        }
        holder.binding.imageviewedit.setOnClickListener {
            val view=LayoutInflater.from(context).inflate(R.layout.foreditlayout,null)
            val edittext:EditText=view.findViewById(R.id.edittextusernamedialog)
            val edittext2:EditText=view.findViewById(R.id.edittextphonedialog)
            edittext.setText(arrayList.get(position).name)
            edittext2.setText(arrayList.get(position).phone)
            val alertDialog=AlertDialog.Builder(context)
            alertDialog.setTitle("Edit")
            alertDialog.setView(view)
            alertDialog.setMessage("${arrayList.get(position).name} edit this user?")
            alertDialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var pushkey=arrayList.get(position).pushkey
                    val user=User(edittext.text.toString(),edittext2.text.toString(),pushkey)
                    databaseReference.child(pushkey).setValue(user)
                }

            })
            alertDialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }

            })
            alertDialog.create().show()
        }
    }
    override fun getItemCount(): Int =arrayList.size

    class UserViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)

}