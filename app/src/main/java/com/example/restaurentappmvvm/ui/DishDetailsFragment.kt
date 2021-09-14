package com.example.restaurentappmvvm.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.viewmodel.AllDishesViewModel
import com.example.restaurentappmvvm.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_dish_details.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class DishDetailsFragment : Fragment() {
    var dishName: String? = null
    var dishDes: String? = null
    var dishPrice: String? = null
    var dishImg: String? = null
    var dishType: String? = null
    var totalQuantity: Int = 1
    var totalPrice: Int = 0
    var actionBar: ActionBar? = null
    var cartViewModel: CartViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        val args: DishDetailsFragmentArgs by navArgs()
        dishName = args.dishTitle
        dishDes = args.dishDescription
        dishPrice = args.dishPrice
        dishImg = args.dishImg
        dishType = args.dishType
        intToolbar()

        tvCategoryDet.text = dishName
        type_tv.text = dishType
        price_tv.text = dishPrice
        des_tv.text = dishDes

        Glide.with(requireActivity()).load(dishImg).into(imgItemDes)

        totalPrice = dishPrice!!.toInt() * totalQuantity

        dish_det_cart_btn.visibility = View.VISIBLE
        added_to_cart_btn.visibility = View.INVISIBLE

        dish_det_cart_btn.setOnClickListener {
            addToCart()
        }
        add_left_bt.setOnClickListener {

            if (totalQuantity < 10) {
                totalQuantity++
                num_inc_tv.text = totalQuantity.toString()
                totalPrice = dishPrice!!.toInt() * totalQuantity

            }
        }
        min_right_bt.setOnClickListener {

            if (totalQuantity > 1) {
                totalQuantity--
                num_inc_tv.text = totalQuantity.toString()
                totalPrice = dishPrice!!.toInt() * totalQuantity

            }
        }

        dish_detailes_buy_now_bt.setOnClickListener {
//            val action = DishDetailesFragmentDirections
//                .actionDishDetailesFragmentToAddressFragment(totalPrice)
//            findNavController().navigate(action)

        }

    }

    fun intToolbar() {
        val toolbar = dish_det_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.title = "Dish details"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun addToCart() {
        var saveCurrentTime: String? = null
        var saveCurrentDate: String? = null
        val callForDate: Calendar = Calendar.getInstance()
        val currentDate: SimpleDateFormat = SimpleDateFormat("MM dd, yyy")
        saveCurrentDate = currentDate.format(callForDate.time)
        var currentTime: SimpleDateFormat = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = currentTime.format(callForDate.time)
        val cartMap = HashMap<String, Any>()
        cartMap["dishTitle"] = tvCategoryDet.text.toString()
        cartMap["dishPrice"] = price_tv.text.toString()
        cartMap["currentTime"] = saveCurrentTime!!
        cartMap["currentDate"] = saveCurrentDate!!
        cartMap["totalQuantity"] = num_inc_tv.text.toString()
        cartMap["totalPrice"] = totalPrice
        cartViewModel!!.addToCart(cartMap).observe(viewLifecycleOwner, {

        })
        Toast.makeText(context, "Added To cart", Toast.LENGTH_LONG).show()
        observerBtCart()
        observerBtAddToCart()
        inc_dec_cl.visibility=View.INVISIBLE


    }
    private fun observerBtCart() {
        cartViewModel!!.getBtCartLiveData().observe(viewLifecycleOwner,
            { btnObserve ->
                if (btnObserve) {
                    dish_det_cart_btn.visibility = View.VISIBLE

                } else {
                    dish_det_cart_btn.visibility = View.INVISIBLE

                }
            })
    }

    private fun observerBtAddToCart() {
        cartViewModel!!.getBtAddToCartLiveData().observe(viewLifecycleOwner,
            { btnObserve ->
                if (btnObserve) {
                    added_to_cart_btn.visibility = View.VISIBLE

                } else {
                    added_to_cart_btn.visibility = View.INVISIBLE

                }
            })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sub_menu, menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_my_cart) {
            view?.findNavController()?.navigate(R.id.action_dishDetailsFragment_to_myCartFragment)
        }
        return true


    }


}