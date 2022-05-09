package com.example.country.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {
    companion object{


        private val PREFERENCE_TIME = "preferences_time"
        private var sharedPreferences : SharedPreferences? = null

        private val lock = Any( )

        @Volatile private var instance: CustomSharedPreferences? = null

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(
            lock){
            instance ?: makeCustomSharedPreferences(context).also {
                instance=it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }


    fun saveTime(time:Long)   //System.nanoTime() tipi Long old için seçildi
    {
      sharedPreferences?.edit(commit = true){
          putLong(PREFERENCE_TIME,time)
      }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCE_TIME,0)
}