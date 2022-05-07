package com.example.country.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.country.databinding.FragmentCountryBinding
import com.example.country.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    lateinit var binding : FragmentCountryBinding
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater)

       return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom()

        //feedfrangemnt ten countryUuid değişkenini countryfragmente yollamak istersem alabileceğim değişken oluşturdum
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        observeLiveData()
    }

    private fun observeLiveData()
    {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country ->
            country?.let {
                binding.countryName.text = country.name
                binding.countryCapital.text = country.capital
                binding.countryCurrency.text = country.currency
                binding.countryLanguage.text = country.language
                binding.countryRegion.text = country.region
            }
        })
    }

}