package com.example.restaurentappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.adapter.AddressAdapter
import com.example.restaurentappmvvm.model.AddressModel
import com.example.restaurentappmvvm.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.fragment_address.*

class AddressFragment : Fragment(), AddressAdapter.SelectedAddress {

    var actionBar: ActionBar? = null
    var addressList = ArrayList<AddressModel>()
    var addressRv: RecyclerView? = null

    var addressAdapter: AddressAdapter? = null
    var addressModel: AddressModel? = null
    var addressViewModel : AddressViewModel ?= null
    var addressN: String = ""
    var totalPriceAmount: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val args: AddressFragmentArgs by navArgs()
        totalPriceAmount = args.totalPrice
        intToolbar()
        loader_address.visibility = View.VISIBLE
        add_addr_now_bt.visibility = View.INVISIBLE
        payment_bt.visibility = View.INVISIBLE

        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        intiAddressRecycleViewAndAdapter()

        add_addr_now_bt.setOnClickListener {
            val action = AddressFragmentDirections.actionAddressFragmentToAddAddressFragment()
            findNavController().navigate(action)
        }
        payment_bt.setOnClickListener {
            val action = AddressFragmentDirections.actionAddressFragmentToPaymentFragment()
            findNavController().navigate(action)

        }
        addressList.clear()
    }

    private fun intToolbar() {
        val toolbar = address_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = " Address"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun intiAddressRecycleViewAndAdapter() {
        addressRv = add_address_rv
        addressRv!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addressAdapter = AddressAdapter(addressList, requireContext(), this)
        addressRv!!.adapter = addressAdapter
        loader_address.visibility = View.INVISIBLE
        add_addr_now_bt.visibility=View.VISIBLE
        payment_bt.visibility=View.VISIBLE
        
        addressViewModel!!.getAddressItems().observe(viewLifecycleOwner,
            { addressModel ->


                addressList.add(addressModel)
                addressAdapter!!.notifyDataSetChanged()

            })
    }

    override fun setAddress(address: String) {
        addressN = address
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_my_cart) {
            view?.findNavController()?.navigate(R.id.action_allDishesFragment_to_myCartFragment)
        }
        return true
    }

}