package com.example.restaurentappmvvm.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.AddressModel
import com.example.restaurentappmvvm.model.DishModel
import com.example.restaurentappmvvm.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_address.*

class AddressRepository(application: Application) {
    private var application = Application()
    private var db = FirebaseFirestore.getInstance()
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var addressModel = AddressModel()
    private var addressLiveData = MutableLiveData<AddressModel>()
    private val addressHashMapLiveData = MutableLiveData<MutableMap<String, String>>()
    private var btnAddAddressObservable = MutableLiveData<Boolean>()

    fun getAddressItems(): MutableLiveData<AddressModel> {
        db.collection("CurrentUser").document(firebaseAuth.currentUser!!.uid)
            .collection("Address").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        addressModel = document.toObject(AddressModel::class.java)
                        addressLiveData.value = addressModel
                    }
                } else {

                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)


                }
            }
        return addressLiveData

    }

    fun addAddress(addressMap: MutableMap<String, String>): MutableLiveData<MutableMap<String, String>> {
        db.collection("CurrentUser").document(firebaseAuth!!.currentUser!!.uid!!)
            .collection("Address").add(addressMap).addOnCompleteListener {

                btnAddAddressObservable.value = false
//                Toast.makeText(application, "Added To cart", Toast.LENGTH_LONG).show()

            }
        return addressHashMapLiveData
    }


}