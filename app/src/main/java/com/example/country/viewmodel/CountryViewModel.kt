package com.example.country.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.country.model.Country

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom()
    {
        val country = Country("Turkey","Asia","Ankara","TL","Turkish","www.ss.com")
        countryLiveData.value = country
    }

}