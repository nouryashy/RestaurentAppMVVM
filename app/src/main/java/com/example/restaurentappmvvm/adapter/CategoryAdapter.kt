package com.example.restaurentappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.model.CategoryModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.category_item_list.view.*


class CategoryAdapter(
    private var categoryList: ArrayList<CategoryModel>,
    var context: Context,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    interface OnItemClickListener {
        fun onClicked(type: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item_list, parent, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.bindItems(categoryList[position])

    }


    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(arrData: ArrayList<CategoryModel>) {
        categoryList = arrData
        notifyDataSetChanged()
    }


    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }



    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var catTitle = itemView.cat_title
        private var catImg = itemView.cat_img


        fun bindItems(categoryModel: CategoryModel) {
            catTitle.text = categoryModel.cat_title
            Glide.with(context).load(categoryModel.cat_img).into(catImg)

            itemView.rootView.setOnClickListener {
                listener.onClicked(categoryModel.type!!)


            }
        }


    }


}