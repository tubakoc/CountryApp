package com.example.country.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.country.model.Country

@Dao
interface CountryDAO {

    //Data Access Object
    @Insert
    suspend fun insertAll(vararg country: Country) : List<Long>
    //suspend -> coroutine durdurulup devam ettirilebilen class
    //List<Long> -> primary keys

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>


    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}