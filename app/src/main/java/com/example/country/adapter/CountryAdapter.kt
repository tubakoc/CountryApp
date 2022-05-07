package com.example.country.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.country.R
import com.example.country.model.Country

class CountryAdapter(val context: Context, val list: ArrayList<Country>) : RecyclerView.Adapter<CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.rvc_country,parent,false)
        return CountryViewHolder(v)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bindData(context,list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //refresh yaparken yenileme
    fun updateCountryList(newCountryList : List<Country>)
    {
        list.clear()
        list.addAll(newCountryList)
        notifyDataSetChanged()
    }
}