package com.example.country.model

import com.google.gson.annotations.SerializedName

/*
Model içerisinde çok fazla işlem yapılacak (intten veri çekmek, roomdb, palete, retrofit, rxjava hepsine model
ile ilgili bişiler yapıiacak Bu nedenle class yerine file olarak oluşturuldu. Tek bir classtan öte bütün işlemleri yapabilecği kotlin file

*/
data class Country(
      @SerializedName("name")
     val name : String?,
      @SerializedName("region")
     val region : String?,
      @SerializedName("capital")
     val capital : String?,
      @SerializedName("currency")
     val currency : String?,
      @SerializedName("language")
     val language : String?,
      @SerializedName("flag")
     val imageUrl : String?)
