package com.example.country.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// AndroidViewModel extend etmek daha güvenli class yapılmıs oluyor appcontext mantığı
//bu classtan obje oluşturmayacağımız ve sadece implement edeceğimiz için abstraact yaptık
abstract class BaseViewModel(application : Application) : AndroidViewModel(application),CoroutineScope {

    //arka planda yapılacak iş
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main    // önce işini yap sonra main thread a dön


    //app destroy olunca işi sonlandır
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}