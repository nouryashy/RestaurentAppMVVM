package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.MyCartModel
import com.example.restaurentappmvvm.repository.CartRepository


class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var cartRepository = CartRepository(application)

    //    private var cartHashMapLiveData = cartRepository.addToCart()
    private var btnCartObservable = cartRepository.getBtCartLiveData()
    private var btnAddToCartObservable = cartRepository.getBtAddToCartLiveData()
    private var  cartItemLiveData=cartRepository.getCartItems()
    private var txtObservable = cartRepository.getTxtLiveData()
    private var clObservable = cartRepository.getClLiveData()
    private var progressObservable = cartRepository.getProgressLiveData()




    fun addToCart(cartMap: HashMap<String, Any>): MutableLiveData<HashMap<String, Any>> {
        return cartRepository.addToCart(cartMap)
    }
    fun getBtCartLiveData(): MutableLiveData<Boolean> {
        return btnCartObservable
    }
    fun getCartItems(): MutableLiveData<MyCartModel> {
        return cartItemLiveData
    }
    fun getBtAddToCartLiveData(): MutableLiveData<Boolean> {
        return btnAddToCartObservable
    }

    fun getTxtLiveData(): MutableLiveData<Boolean> {
        return txtObservable
    }
    fun getClLiveData(): MutableLiveData<Boolean> {
        return clObservable
    }
    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressObservable
    }
}