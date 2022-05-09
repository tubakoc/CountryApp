package com.example.country.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*
Model içerisinde çok fazla işlem yapılacak (intten veri çekmek, roomdb, palete, retrofit, rxjava hepsine model
ile ilgili bişiler yapıiacak Bu nedenle class yerine file olarak oluşturuldu. Tek bir classtan öte bütün işlemleri yapabilecği kotlin file

*/

//sqlite içerisinde room dn kullanmak için entity yapısı kullanılmalı. SqLite table içerisinde map işlemi yapar
//Entity constructor da primarykey tanımlamamalıyız nedeni her model olusturunca primarykey ister
@Entity
data class Country(
      @ColumnInfo(name = "name")
      @SerializedName("name")
     val name : String?,

      @ColumnInfo(name = "region")
      @SerializedName("region")
     val region : String?,

      @ColumnInfo(name = "capital")
      @SerializedName("capital")
     val capital : String?,

      @ColumnInfo(name = "currency")
      @SerializedName("currency")
     val currency : String?,

      @ColumnInfo(name = "language")
      @SerializedName("language")
     val language : String?,

      @ColumnInfo(name = "flag")
      @SerializedName("flag")
     val imageUrl : String?)
{
    @PrimaryKey(autoGenerate = true)  //otomatik oluşturacak id
    var uuid : Int = 0
}
