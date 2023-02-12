package com.x7.kotlin_firebase_read_arraylist.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun personDao():PersonDao
}