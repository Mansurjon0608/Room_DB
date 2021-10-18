package Db

import DAO.PersonDao
import Entity.Person
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun personDao():PersonDao

    companion object{
        private var instance:AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{

            if (instance== null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "db_name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }



}