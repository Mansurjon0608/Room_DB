package DAO

import Entity.Person
import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao

interface PersonDao {

    @Query("select * from person")
    fun getAllPerson():List<Person>

    @Insert
    fun addPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Update
    fun updatePerson(person: Person)


}