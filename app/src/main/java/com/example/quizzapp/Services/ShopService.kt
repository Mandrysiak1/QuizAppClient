package com.example.quizzapp.Services

import com.example.quizzapp.objects.BuyItemRequest
import com.example.quizzapp.objects.Responses.AllItemsResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopService{

    @POST("shop/buyItem")
    fun buyItem(@Body body : BuyItemRequest) : retrofit2.Call<ResponseBody>

    @POST("shop/getAllItems")
    fun getAllItems(@Body body : BuyItemRequest) : retrofit2.Call<AllItemsResponse>

    @POST("shop/setSelectedItem")
    fun setSelected(@Body body : BuyItemRequest) : retrofit2.Call<ResponseBody>

}