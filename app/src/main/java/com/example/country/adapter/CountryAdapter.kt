package com.example.country.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.country.R
import com.example.country.databinding.RvcCountryBinding
import com.example.country.model.Country
import com.example.country.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.rvc_country.view.*

class CountryAdapter(val context: Context, val list: ArrayList<Country>) : RecyclerView.Adapter<CountryViewHolder>(),CountryClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val v = LayoutInflater.from(context).inflate(R.layout.rvc_country,parent,false)
        val view = DataBindingUtil.inflate<RvcCountryBinding>(inflater,R.layout.rvc_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
       // holder.bindData(context,list.get(position))
        holder.view.country = list[position]
        holder.view.listener = this
        
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

    override fun onCountryClicked(v: View) {
        //val uuid = RvcCountryBinding.bind(v).countryUuidText.text.toString().toInt()
        val uuid = v.countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}