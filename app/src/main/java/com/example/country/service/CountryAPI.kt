package com.example.country.service

import com.example.country.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //Retrofitte yapılacak işlemler bu interface de yazılacaktır.
    //GET, POST
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    //BASE_URL -> https://raw.githubusercontent.com/
    //EXTENSION ->atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json


    //veriyi sadece bi kere alacağımız için SİNGLE api daki veri güncellenmiyor
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>
}