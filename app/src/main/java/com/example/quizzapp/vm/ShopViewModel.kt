package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.BuyItemRequest
import com.example.quizzapp.objects.Responses.AllItemsResponse
import com.example.quizzapp.objects.ShopItem

class ShopViewModel(application: Application) : AndroidViewModel(application) {


    val currentItem : MutableLiveData<ShopItem> by lazy {
        MutableLiveData<ShopItem>()
    }

    val playersItems : MutableLiveData<AllItemsResponse> by lazy {
        MutableLiveData<AllItemsResponse>()
    }
    val succesful : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    var currentItemPosition : Int = -1

    fun getPlayerItems()
    {
        NetModule.getAllItems(BuyItemRequest(),playersItems)
    }

    fun buyItem(){
       var  x = BuyItemRequest()
        x.price = currentItem.value?.price?.toInt()!!
        x.index = currentItem.value!!.id
        NetModule.buyItem(x,succesful,playersItems)
    }

    fun setAvatar(itemID : Int)
    {
        var  x = BuyItemRequest()

        x.index = currentItem.value!!.id
        NetModule.setSelected(x)
    }
}
