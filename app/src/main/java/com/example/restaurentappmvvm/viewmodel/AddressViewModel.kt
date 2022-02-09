package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.AddressModel
import com.example.restaurentappmvvm.repository.AddressRepository
import com.example.restaurentappmvvm.repository.CartRepository

class AddressViewModel(application: Application) : AndroidViewModel(application) {
    private var addressRepository = AddressRepository(application)
    private var  addressLiveData=addressRepository.getAddressItems()


    fun getAddressItems(): MutableLiveData<AddressModel> {

        return addressLiveData
    }


    fun addAddress(addressMap: MutableMap<String, String>): MutableLiveData<MutableMap<String, String>> {
        return addressRepository.addAddress(addressMap)
    }
}