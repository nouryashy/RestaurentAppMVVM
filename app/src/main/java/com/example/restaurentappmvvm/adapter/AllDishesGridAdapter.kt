package com.example.restaurentappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.model.DishModel
import kotlinx.android.synthetic.main.dish_item_list.view.*

class AllDishesGridAdapter (
    private var dishList: ArrayList<DishModel>,
    var context: Context,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<AllDishesGridAdapter.AllDishesGridViewHolder>() {


    interface OnItemClickListener {
        fun onClickedG(dishModel: DishModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDishesGridViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_dish_item_list, parent, false)
        return AllDishesGridViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllDishesGridViewHolder, position: Int) {
        holder.bindItems(dishList[position])
    }

    override fun getItemCount(): Int {
        return dishList.size
    }

    fun setData(arrData: ArrayList<DishModel>) {
        dishList = arrData
        notifyDataSetChanged()
    }

    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }


    inner class AllDishesGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dishTitle = itemView.dish_title_tv
        private var dishPrice = itemView.dish_price_tv
        private var dishType = itemView.dish_type_tv
        private var dishImg = itemView.dish_iv


        fun bindItems(dishModel: DishModel) {
            dishTitle.text = dishModel.dish_title
            dishPrice.text = dishModel.price
            dishType.text = dishModel.type
            Glide.with(context).load(dishModel.dish_img).into(dishImg)

            itemView.rootView.setOnClickListener {
                listener.onClickedG(dishModel)
            }
        }


    }


}