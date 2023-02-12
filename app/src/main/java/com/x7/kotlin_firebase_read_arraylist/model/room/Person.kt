package com.x7.kotlin_firebase_read_arraylist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.x7.kotlin_firebase_read_arraylist.utils.Constants.TABLE_NAMEE

@Entity(tableName = TABLE_NAMEE)
class Person constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val uid:Int,
    @ColumnInfo(name = "personname")
    val name:String,
    @ColumnInfo(name = "personphone")
    val phone:String,

)