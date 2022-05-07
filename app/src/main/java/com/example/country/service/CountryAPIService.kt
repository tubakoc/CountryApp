package com.example.country.service

import com.example.country.model.Country
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {
    //BASE_URL -> https://raw.githubusercontent.com/
    //EXTENSION ->atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    //sonradan değiştirilmeyen değişkenler büyük harflerle yazılır
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())  //gson kullanılacağı oyle cevireceğini belirtmemiz gerekir.
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //rxjava kullanacağımızı belirtmelyiz
        .build()
        .create(CountryAPI::class.java)

    fun getData() : Single<List<Country>>
    {
        return api.getCountries()
    }
}