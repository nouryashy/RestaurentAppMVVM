package com.example.restaurentappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.model.AddressModel
import kotlinx.android.synthetic.main.address_item_list.view.*

class AddressAdapter (
    private var addressList: ArrayList<AddressModel>,
    var context: Context, var selectedAddress: SelectedAddress
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    var selectedRadioBtn: RadioButton? = null

    interface SelectedAddress {
        fun setAddress(address: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.address_item_list, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bindItems(addressList[position])
    }


    override fun getItemCount(): Int {
        return addressList.size
    }
//    fun setData(arrData: List<DishModel>) {
//        dishList = arrData as ArrayList<DishModel>
//    }


    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var address = itemView.address_add
        private var radioButton = itemView.select_address
//        private var addressRS = itemView.select_address


        fun bindItems(addressModel: AddressModel) {

            address.text = addressModel.userAddress
            radioButton.setOnClickListener {
                for (address in addressList) {
                    address.isSelected = false
                }
                addressList[position].isSelected = true

                if (selectedRadioBtn != null) {
                    selectedRadioBtn!!.isChecked = false
                }
                //            addressRS = addressR
                selectedRadioBtn=radioButton
                selectedRadioBtn!!.isChecked = true
                selectedAddress.setAddress(addressModel.userAddress!!)
            }


        }


    }

}