package com.example.country.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.country.R
import com.example.country.model.Country
import com.example.country.util.downloadFromUrl
import com.example.country.util.placeholderProgressBar
import com.example.country.view.FeedFragmentDirections

class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var travelImage : ImageView
    var countryName : TextView
    var countryRegion : TextView

    init {
        travelImage = itemView.findViewById(R.id.travelImage)
        countryName = itemView.findViewById(R.id.countryName)
        countryRegion = itemView.findViewById(R.id.countryRegion)


    }

    fun bindData(context: Context,country: Country)
    {
        countryName.text = country.name
        countryRegion.text = country.region
        travelImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(context))
        itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(country.uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
}