package com.example.restaurentappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.my_cart_item_list.view.*

class MyCartAdapter(private var cartList: ArrayList<MyCartModel>,
                    var context: Context,

                    ) : RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder>() {

    var totalAmount: Int? = 0
    var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClicked(totalPrice: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_cart_item_list, parent, false)
        return MyCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        holder.bindItems(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    fun grandTotal(cartList: ArrayList<MyCartModel>): Int {
        totalAmount = 0
        for (i in 0 until cartList.size) {
            totalAmount = totalAmount!! + cartList[i].totalPrice!!
        }


        return totalAmount!!
    }

    fun setData(arrData: ArrayList<MyCartModel>) {
        cartList = arrData
        notifyDataSetChanged()
    }


    inner class MyCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dishTitle = itemView.cart_name_tv
        private var dishPrice = itemView.cart_price_tv
        private var dishQuantity = itemView.cart_quan_tv
        private var dishTotalPrice = itemView.cart_total_price_tv
        private var date = itemView.cart_date_tv
        private var time = itemView.cart_time_tv
        private var delete = itemView.delet_bt


        fun bindItems(cartModel: MyCartModel) {
            dishTitle.text = cartModel.dishTitle
            dishPrice.text = cartModel.dishPrice
            dishQuantity.text = cartModel.totalQuantity
            dishTotalPrice.text = cartModel.totalPrice.toString()
            date.text = cartModel.currentDate
            time.text = cartModel.currentTime



            delete.setOnClickListener {

                firestore.collection("AddToCart").document(firebaseAuth.currentUser!!.uid)
                    .collection("User").document(cartList[position].documentId!!)
                    .delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cartList.remove(cartList[position])
                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_LONG).show()
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    }

                listener!!.onClicked(cartModel.totalPrice!!)


            }
        }
    }


}