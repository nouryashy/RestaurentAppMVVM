package com.example.restaurentappmvvm.ui

import android.content.Context
import android.database.DatabaseErrorHandler
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.adapter.MyCartAdapter
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.MyCartModel
import com.example.restaurentappmvvm.viewmodel.AllDishesViewModel
import com.example.restaurentappmvvm.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_all_dishes.*
import kotlinx.android.synthetic.main.fragment_my_cart.*


class MyCartFragment : Fragment() {
    var cartList = ArrayList<MyCartModel>()
    var myCartRv: RecyclerView? = null
    var myCartAdapter: MyCartAdapter? = null
    var total: String? = null
    var actionBar: ActionBar? = null
    var cartViewModel: CartViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intToolbar()
        toggleViews(View.VISIBLE,View.INVISIBLE,View.INVISIBLE)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        intiMyCartRecycleViewAndAdapter()
        myCartAdapter!!.setClickListener(onClicked)
        cart_buy_now_bt.setOnClickListener {
            val action = MyCartFragmentDirections.actionMyCartFragmentToAddressFragment(total!!.toInt())
            findNavController().navigate(action)

        }
        cartList.clear()

    }

//    private fun checkListEmpty() {
//        total = (myCartAdapter!!.grandTotal(cartList)).toString()
//        val totalI = total!!.toInt()
//        if (totalI == 0) {
//            rc_and_bt_cl.visibility = View.INVISIBLE
//            loader_my_cart.visibility = View.INVISIBLE
//            noItems.visibility = View.VISIBLE
//        } else {
//            total_price_tv_all.text = "Total Amount : $total $"
//        }
//
//
//    }

    private fun intToolbar() {
        val toolbar = mycart_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "My Cart"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private val onClicked = object : MyCartAdapter.OnItemClickListener {
        override fun onClicked(totalPrice: Int) {
            total = (myCartAdapter!!.grandTotal(cartList) - totalPrice).toString()
            val totalI = total!!.toInt()
            if (totalI == 0) {
                rc_and_bt_cl.visibility = View.INVISIBLE
                loader_my_cart.visibility = View.INVISIBLE
                noItems.visibility = View.VISIBLE
            } else {
                total_price_tv_all.text = "Total Amount : $total $"

            }


        }

    }

    private fun intiMyCartRecycleViewAndAdapter() {

        myCartRv = cart_rv
        myCartRv!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myCartAdapter = MyCartAdapter(cartList, requireContext())
        myCartRv!!.adapter = myCartAdapter
        cartViewModel!!.getCartItems().observe(viewLifecycleOwner,
            { cartModel ->
//                toggleViews(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)
                observerTv()
                observerCl()
                observerProg()
                cartList.add(cartModel)
                myCartAdapter!!.notifyDataSetChanged()
                total = (myCartAdapter!!.grandTotal(cartList)).toString()
                total_price_tv_all.text = "Total Amount : $total $"

//                if (myCartRv!!.itemDecorationCount != 0) {
//                    loader_my_cart.visibility = View.INVISIBLE
//                    rc_and_bt_cl.visibility = View.VISIBLE
//                    observerTv()
//                } else {
//                    loader_my_cart.visibility = View.INVISIBLE
//                    rc_and_bt_cl.visibility = View.INVISIBLE
//                }
            })



        when (cartList.size) {
            0 ->
                toggleViews(View.INVISIBLE,View.INVISIBLE,View.VISIBLE)
        else ->
                toggleViews(View.INVISIBLE,View.VISIBLE,View.INVISIBLE)

        }



    }

    private fun observerTv() {
        cartViewModel!!.getTxtLiveData().observe(viewLifecycleOwner,
            { txObserver ->
                if (txObserver) {
                    noItems.visibility = View.VISIBLE
                } else {
                    noItems.visibility = View.INVISIBLE
                }

            })
    }

    private fun observerCl() {
        cartViewModel!!.getClLiveData().observe(viewLifecycleOwner,
            { clObserver ->
                if (clObserver) {
                    rc_and_bt_cl.visibility = View.VISIBLE
                } else {
                    rc_and_bt_cl.visibility = View.INVISIBLE
                }
            })
    }

    private fun observerProg() {
        cartViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
            { progObserver ->
                if (progObserver) {
                    loader_my_cart.visibility = View.VISIBLE
                } else {
                    loader_my_cart.visibility = View.INVISIBLE
                }
            })
    }

    fun toggleViews(loaderC: Int, rcCL:Int, noItemsData: Int) {

        loader_my_cart.visibility = loaderC
        rc_and_bt_cl.visibility=rcCL
        noItems.visibility = noItemsData
    }


}