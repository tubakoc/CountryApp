package com.example.country.viewmodel

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.country.databinding.FragmentFeedBinding
import com.example.country.model.Country
import com.example.country.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

   // private lateinit var countryLoading : ProgressBar
    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()  //callleri clean etme

    //dataları liveData olarak tutacaz
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>() //hata ya olacak ya olmicak bu yüzden boolean
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData()
    {
        getDataFromAPI()

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
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }
}