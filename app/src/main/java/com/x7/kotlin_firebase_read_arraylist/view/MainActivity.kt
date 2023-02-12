package com.x7.kotlin_firebase_read_arraylist.view

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.database.*
import com.x7.kotlin_firebase_read_arraylist.databinding.ActivityMainBinding
import com.x7.kotlin_firebase_read_arraylist.model.User
import com.x7.kotlin_firebase_read_arraylist.model.room.AppDatabase
import com.x7.kotlin_firebase_read_arraylist.model.room.Person
import com.x7.kotlin_firebase_read_arraylist.model.room.PersonDao
import com.x7.kotlin_firebase_read_arraylist.utils.Constants.TABLE_NAMEE

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var databaseReference: DatabaseReference
    val arrayList=ArrayList<User>()
    lateinit var userAdapter: UserAdapter
    lateinit var personadapter: PersonAdapter
    lateinit var personDao: PersonDao
    var livedata= MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "$TABLE_NAMEE"
        ).allowMainThreadQueries().build()
        personDao=db.personDao()

        livedata.value=false

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users")

        binding.apply {

            binding.imageviewadd.setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }
        }

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                personDao.deleteAllPersons()

                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user=datasnapshot.getValue(User::class.java)
                    arrayList.add(user!!)
                }
                livedata.value=true
                //Room
                    personDao.deleteAllPersons()
                    for (i in 0 until  arrayList.size){
                        val person=Person(0,arrayList.get(i).name,arrayList.get(i).phone)
                        personDao.insertPerson(person)
                    }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        livedata.observe(this,{
            if (it){
                binding.textviewinternetconnection.text="User List"
                userAdapter= UserAdapter(this@MainActivity,arrayList)
                binding.apply {
                    recyclerview1.layoutManager=LinearLayoutManager(this@MainActivity)
                    recyclerview1.adapter=userAdapter
                }
            }else{
                binding.textviewinternetconnection.text="Connecting..."
                personadapter= PersonAdapter(this@MainActivity,personDao.getAllPersons() as ArrayList<Person>)
                binding.apply {
                    recyclerview1.layoutManager=LinearLayoutManager(this@MainActivity)
                    recyclerview1.adapter=personadapter
                }
            }
        })

        observeinternetConnection()

    }




    fun observeinternetConnection(){

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                livedata.postValue(true)
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                livedata.postValue(false)
            }
        }
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)


    }

}