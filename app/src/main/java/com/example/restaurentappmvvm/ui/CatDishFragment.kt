package com.example.restaurentappmvvm.ui

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.adapter.AllDishesAdapter
import com.example.restaurentappmvvm.adapter.AllDishesGridAdapter
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.DishModel
import com.example.restaurentappmvvm.viewmodel.AllDishesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_all_dishes.*
import kotlinx.android.synthetic.main.fragment_cat_dish.*


class CatDishFragment : Fragment() {
    var dishList = ArrayList<DishModel>()
    var catDishRecView: RecyclerView? = null

    var allDishAdapter: AllDishesAdapter? = null
    var allDishGridAdapter: AllDishesGridAdapter? = null
    var allDishedViewModel: AllDishesViewModel? = null
    var dishModel: DishModel? = null
    var categoryModel: CategoryModel? = null

    var db: FirebaseFirestore? = null

    var type: String? = null
    var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat_dish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val args: CatDishFragmentArgs by navArgs()
        type = args.type
        intToolbar()

        controlButtonsAtVerView()
        loader_cat_dishes.visibility = View.VISIBLE
        allDishedViewModel = ViewModelProviders.of(this).get(AllDishesViewModel::class.java)
        intiAllDishRecycleViewAndAdapter()
        fillVerAdap()

        ver_c_l_bt.setOnClickListener {
            controlButtonsAtVerView()
            intiAllDishRecycleViewAndAdapter()
        }


        grid_c_l_bt.setOnClickListener {
            controlButtonsAtGridView()
            intiGridAllDishRecycleViewAndAdapter()
            allDishGridAdapter!!.setClickListener(onClickedG)
        }
        allDishAdapter!!.setClickListener(onClicked)
        dishList.clear()
    }


    private fun intToolbar() {
        val toolbar = cat_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.title = "$type Dishes"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private val onClicked = object : AllDishesAdapter.OnItemClickListener {
        override fun onClicked(dishModel: DishModel) {


            val dishName = dishModel.dish_title
            val dishDes = dishModel.dish_description
            val dishPrice = dishModel.price
            val dishImg = dishModel.dish_img
            val dishType = dishModel.type

            val action = CatDishFragmentDirections.actionCatDishFragmentToDishDetailsFragment(
                dishDes!!,
                dishType!!,
                dishPrice!!,
                dishName!!,
                dishImg!!
            )
            findNavController().navigate(action)
        }


    }


    private val onClickedG = object : AllDishesGridAdapter.OnItemClickListener {
        override fun onClickedG(dishModel: DishModel) {
            val dishName = dishModel.dish_title
            val dishDes = dishModel.dish_description
            val dishPrice = dishModel.price
            val dishImg = dishModel.dish_img
            val dishType = dishModel.type


            val action3 = CatDishFragmentDirections.actionCatDishFragmentToDishDetailsFragment(
                dishDes!!,
                dishType!!,
                dishPrice!!,
                dishName!!,
                dishImg!!
            )
            findNavController().navigate(action3)
        }

    }


    private fun intiAllDishRecycleViewAndAdapter() {
        catDishRecView = cat_dishes_rv
        catDishRecView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        allDishAdapter = AllDishesAdapter(dishList, requireContext(),onClicked)
        catDishRecView!!.adapter = allDishAdapter

    }

    private fun fillVerAdap() {
        allDishedViewModel!!.getDishByCateLiveData(type!!).observe(viewLifecycleOwner,
            { dishModel ->
                observerPro()
                dishList.add(dishModel)
                allDishAdapter!!.notifyDataSetChanged()
            })

    }

    private fun intiGridAllDishRecycleViewAndAdapter() {
        catDishRecView = cat_dishes_rv
        var gridLayoutManager = GridLayoutManager(context, 2)
        catDishRecView!!.layoutManager = gridLayoutManager
        allDishGridAdapter = AllDishesGridAdapter(dishList, requireContext(),onClickedG)
        catDishRecView!!.adapter = allDishGridAdapter

    }


    private fun observerPro() {
        allDishedViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
            { progressObserver ->
                if (progressObserver) {
                    loader_cat_dishes.visibility = View.VISIBLE
                } else {
                    loader_cat_dishes.visibility = View.INVISIBLE
                }
            })
    }

    private fun controlButtonsAtVerView() {
        ver_c_d_bt!!.visibility = View.VISIBLE
        ver_c_l_bt!!.visibility = View.INVISIBLE
        grid_c_d_bt!!.visibility = View.INVISIBLE
        grid_c_l_bt!!.visibility = View.VISIBLE
    }

    private fun controlButtonsAtGridView() {
        grid_c_d_bt!!.visibility = View.VISIBLE
        grid_c_l_bt!!.visibility = View.INVISIBLE
        ver_c_d_bt!!.visibility = View.INVISIBLE
        ver_c_l_bt!!.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sub_menu, menu)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_my_cart) {
            view?.findNavController()?.navigate(R.id.action_catDishFragment_to_myCartFragment)
        }
        return true


    }


}
