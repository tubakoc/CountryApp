package com.example.country.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.country.model.Country

@Database(entities = arrayOf(Country::class),version = 1)
abstract class CountryDatabase : RoomDatabase(){

    abstract fun countryDao() : CountryDAO

    //Singleon -> İçerisinde tek bir obje oluşturulabilen class (instance). (operator olarak oluşturduğumuz classda yapacak)Classta obje yoksa oluşturur varsa o obje çekilir.

    companion object{

      @Volatile  private var instance :CountryDatabase? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){  //varsa instance döner yoksa synchronized çalısacsak
            instance ?: makeDatabase(context).also {
                instance = it
            }

        }

       // context.applicationContext fragment activity contexti kullanmak istemiyorumdestroy edilmesi durumunda
        //app çökmesin direk appin contexti verildi. context.applicationContext
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }
}