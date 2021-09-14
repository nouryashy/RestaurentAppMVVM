package com.example.restaurentappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.DishModel
import kotlinx.android.synthetic.main.dish_item_list.view.*

class PopularDishesAdapter(
    private var dishList: ArrayList<DishModel>,
    var context: Context, var listener: OnItemClickListener
) : RecyclerView.Adapter<PopularDishesAdapter.PopularDishesViewHolder>() {

    private val limit = 4

    interface OnItemClickListener {
        fun onClicked1(dishModel: DishModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularDishesViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dish_item_list, parent, false)
        return PopularDishesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularDishesViewHolder, position: Int) {
        holder.bindItems(dishList[position])
    }

    override fun getItemCount(): Int {
        if (dishList.size > limit) {
            return limit;
        } else {
            return dishList.size;
        }

    }
//    fun setData(arrData: ArrayList<DishModel>) {
//        dishList = arrData
//        notifyDataSetChanged()
//    }

    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }


    inner class PopularDishesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dishTitle = itemView.dish_title_tv
        private var dishPrice = itemView.dish_price_tv
        private var dishType = itemView.dish_type_tv
        private var dishImg = itemView.dish_iv

        fun bindItems(dishList: DishModel) {
            dishTitle.text = dishList.dish_title
            dishPrice.text = dishList.price
            dishType.text = dishList.type
            Glide.with(context).load(dishList.dish_img).into(dishImg)
            itemView.rootView.setOnClickListener {
                listener.onClicked1(dishList)


            }

        }


    }


}