package com.x7.kotlin_firebase_read_arraylist.model.room

import androidx.room.*
import com.x7.kotlin_firebase_read_arraylist.utils.Constants.TABLE_NAMEE

@Dao
interface PersonDao {
    @Query("SELECT * FROM $TABLE_NAMEE")
    fun getAllPersons():List<Person>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: Person)
    @Update
    fun updatePerson(user: Person)
    @Delete
    fun deletePerson(user: Person)
    @Query("DELETE FROM $TABLE_NAMEE")
    fun deleteAllPersons()
    @Query("SELECT * FROM $TABLE_NAMEE WHERE id=:idd ")
    fun loadbyid(idd:Int):Person
}