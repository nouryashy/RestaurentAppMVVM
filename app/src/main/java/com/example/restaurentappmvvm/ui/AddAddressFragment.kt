package com.example.restaurentappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.viewmodel.AddressViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_address.*


class AddAddressFragment : Fragment() {

    var actionBar: ActionBar? = null


    var db: FirebaseFirestore? = null
    var firebaseAuth: FirebaseAuth? = null
    var totalPrice: Int = 0

    var addressViewModel : AddressViewModel?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: AddAddressFragmentArgs by navArgs()
        totalPrice = args.totalPrice
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        intToolbar()
        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
    }
    fun intToolbar() {
        val toolbar = addAddress_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "Add Address"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }

        ad_add_address.setOnClickListener {
            addAddress()
        }
    }

    fun addAddress(){
        val userName = ad_name.text
        val userCity = ad_city.text
        val userAddress = ad_address.text
        val userCode = ad_code.text
        val userNumber = ad_phone.text

        var finalAddress = ""

        if (!userName.isEmpty()) {
            finalAddress += userName
        }
        if (!userCity.isEmpty()) {
            finalAddress += userCity
        }
        if (!userAddress.isEmpty()) {
            finalAddress += userAddress
        }
        if (!userCode.isEmpty()) {
            finalAddress += userCode
        }
        if (!userNumber.isEmpty()) {
            finalAddress += userNumber

        }

        if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {
            val map =  mutableMapOf<String, String>()
            map["userAddress"] = finalAddress

            addressViewModel!!.addAddress(map).observe(viewLifecycleOwner, {

            })
            Toast.makeText(activity, "Address added", Toast.LENGTH_LONG).show()

            val action = AddAddressFragmentDirections.actionAddAddressFragmentToAddressFragment(
                                    totalPrice
                                )
                            findNavController().navigate(action)

        }
    }


}