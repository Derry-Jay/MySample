package com.my.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.sample.databinding.DashboardFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.my.sample.R
import com.my.sample.adapters.ProductsAdapter
import com.my.sample.extensions.*
import com.my.sample.models.Product
import com.my.sample.models.ProductBase

class DashboardFragment: Fragment() {

    private var _binding: DashboardFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashboardFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this creates a vertical layout Manager
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        // ArrayList of class City
        var data: ArrayList<Product>
        val call = view.stringFromResource(R.string.base_url_4).retrofitInstance.apiLink.getProducts()
        call.enqueue(object : Callback<ProductBase> {
            override fun onResponse(
                call: Call<ProductBase>,
                response: Response<ProductBase>
            ) {
                try {
                    println(response.message())

                    data = response.body()?.products as ArrayList<Product>? ?: ArrayList()
                    println(data)

                    binding.recyclerview.adapter = ProductsAdapter(data)

                    return view.refreshDrawableState()
                } catch (e: Exception) {
                    return e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ProductBase>, t: Throwable) {
                t.printStackTrace()
                return view.showSnackBar(t.message ?: "", 2, t.localizedMessage ?: "", null,null)
            }
        })

//        val j = GlobalScope.launch {
//            val result = ut.api.getCities()
//            data = result.body()?.cities ?: ArrayList<City>()
//        }
//
//        j.invokeOnCompletion {
//        }

        binding.buttonSecond.setOnClickListener {
            it?.navCon?.navigate(R.id.action_DashboardFragment_to_GalleryFragment)
        }

//        binding.textviewSecond.text = "Hello"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}