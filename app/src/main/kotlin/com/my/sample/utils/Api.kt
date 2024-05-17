package com.my.sample.utils

import com.my.sample.models.ProductBase
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("products")
    fun getProducts() : Call<ProductBase>
}