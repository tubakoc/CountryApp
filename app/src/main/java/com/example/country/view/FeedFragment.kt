package com.example.country.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.country.adapter.CountryAdapter
import com.example.country.databinding.FragmentFeedBinding
import com.example.country.viewmodel.FeedViewModel

//bi viewmodel olşturup ve viewmodelın datalarını incelememiz gerekiyo

class FeedFragment : Fragment() {
    lateinit var binding : FragmentFeedBinding
    private lateinit var viewModel : FeedViewModel
    lateinit var  countryAdapter : CountryAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModelProviders hangi fragmentte veya activity de old veya hangi viewmodel classı ile çalıştığımızı deriz
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        countryAdapter = CountryAdapter(requireActivity(), arrayListOf())

       // binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter

        binding.swipeRefresh.setOnRefreshListener {
            swipeRefresh()
        }

        observeLiveData()
    }

    private fun swipeRefresh() {
        binding.countryList.visibility = View.GONE
        binding.countryError.visibility = View.GONE
        binding.countryLoading.visibility = View.VISIBLE
        viewModel.refreshData()
        binding.swipeRefresh.isRefreshing = false
    }


    //verileri gözlemlediğimizden emin olmamız gerekiyor. Gözlemlemek içn viewmodel kullanılacak .observe metoduyla
    //mutableLiveData nın getirdiği özellik
    private fun observeLiveData ()
    {

        //observe() ilk parametre lifecycleowneerı kim 2. parametre Observer it neden Country viewmodelde mutableLiveDatatipi old için
        //it nullable şekilde gelmiyo gelecekdatanın nullable olup olmadığınıbilemeyiz bu yüzden countries değişkeni yazdım
        //listeyi değişkene atamaa yapılacak
        //this yerine viewLifecycleOwner 2020 sonrası
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                //boolean true ise
                if (it)
                {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }
                else {
                    binding.countryLoading.visibility = View.GONE

                }
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                }
                else {
                    binding.countryError.visibility = View.GONE

                }
            }
        })
    }


}