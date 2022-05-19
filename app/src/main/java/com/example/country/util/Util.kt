package com.example.country.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.country.R

//Extension

fun ImageView.downloadFromUrl(url : String?,progressDrawable: CircularProgressDrawable)
{
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

//textler webdeb hızlı inerken image yavas iner
fun placeholderProgressBar(context: Context) : CircularProgressDrawable
{
    return CircularProgressDrawable(context).apply {
        //progresbar özellikleri belirleme
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}


//imageleri çekme  BindingAdapter xml de çalışılabilir hale getirmwk için eklendi
@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView,url: String?){
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}