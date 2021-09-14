package com.example.restaurentappmvvm.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class CartRepository(application: Application) {
    private var application = Application()
    private var db = FirebaseFirestore.getInstance()
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var btnCartObservable = MutableLiveData<Boolean>()
    private var btnAddToCartObservable = MutableLiveData<Boolean>()
    private val cartHashMapLiveData = MutableLiveData<HashMap<String, Any>>()
    private var cartLiveData = MutableLiveData<MyCartModel>()
    private var cartModel = MyCartModel()
    private var txtObservable = MutableLiveData<Boolean>()
    private var txtObservable2 = MutableLiveData<Boolean>()
    private var progressObservable = MutableLiveData<Boolean>()
    private var clObservable = MutableLiveData<Boolean>()
    private var cartList= ArrayList<MyCartModel>()


    fun addToCart(cartMap: HashMap<String, Any>): MutableLiveData<HashMap<String, Any>> {
        db.collection("AddToCart").document(firebaseAuth.currentUser!!.uid)
            .collection("User").add(cartMap).addOnCompleteListener {

                btnCartObservable.value = false
                btnAddToCartObservable.value = true
//                Toast.makeText(application, "Added To cart", Toast.LENGTH_LONG).show()


            }
        return cartHashMapLiveData
    }

    fun getCartItems(): MutableLiveData<MyCartModel> {
        db.collection("AddToCart").document(firebaseAuth.currentUser!!.uid)
            .collection("User")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    if (cartList.isEmpty()){
//                        txtObservable.value=true
//                        clObservable.value = false
//                        progressObservable.value = false
//                    }
                    txtObservable.value=false
                    clObservable.value = true
                    progressObservable.value = false

                    for (documentSnapshot: DocumentSnapshot in task.result!!.documents) {
                        val documentId: String = documentSnapshot.id
                        cartModel = documentSnapshot.toObject(MyCartModel::class.java)!!
                        //to select the document by id
                        cartModel.documentId = documentId
                        cartLiveData.value = (cartModel)

                    }


                } else {

                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
        return cartLiveData
    }

    fun getBtCartLiveData(): MutableLiveData<Boolean> {
        return btnCartObservable
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