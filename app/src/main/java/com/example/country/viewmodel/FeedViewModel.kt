package com.example.country.viewmodel

import android.app.Application
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.country.databinding.FragmentFeedBinding
import com.example.country.model.Country
import com.example.country.service.CountryAPIService
import com.example.country.service.CountryDatabase
import com.example.country.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

   // private lateinit var countryLoading : ProgressBar
    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()  //callleri clean etme
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //nanosn cinsinden 10 dk

    //dataları liveData olarak tutacaz
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>() //hata ya olacak ya olmicak bu yüzden boolean
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData()
    {
        //geçen zamanı kontrol etme
        val updateTime = customPreferences.getTime() // ne zamana kaydedildiğinibundan biliyoz

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime <refreshTime)
        {
            getDataFromSQLite()
        }else
        {
            getDataFromAPI()
        }


        /*
         Livedata
        bi yerden veri çekmiceğimiz için veriyi kendimiz eklicez
        val country = Country("Turkey","Asia","Ankara","Try","Turkish","www.dd.com")
        val country2 = Country("France","Europe","Paris","Eur","French","www.dd.com")
        val country3 = Country("Germany","Europe","Berlin","Eur","German","www.dd.com")


        val countryList = arrayListOf<Country>(country,country2,country3)

        //MutableLive old için countries = countryList diyemiyoruz
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
        */
    }

    fun refreshFromAPI()
    {
        //swipe refresh de apiden veri alalım
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
      //dao işlemleri coroutine içerisinde yapmamız gerekir
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_LONG).show()
        }
    }




    //observe de onSucces ve onError yok Single de var
    private fun getDataFromAPI()
    {
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>)
    {
        //corotines kullanılcak baseviewmodel extend edildikten sonra corotine kullanılabilinecek
        launch {
            //neden dao classından obje olusturmak yerine bu yapıldı??
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // -> list -> individual
            var i = 0
            while (i<list.size)
            {
                list[i].uuid = listLong[i].toInt() //return edilen long uuid olarak tanımlama
                i += 1
            }

            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    //hafızayı daha verimli hale getirme
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}