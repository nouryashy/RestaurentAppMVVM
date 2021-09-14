package com.example.restaurentappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
import kotlinx.android.synthetic.main.fragment_home.*


class AllDishesFragment : Fragment() {

    var dishList = ArrayList<DishModel>()
    var allDishRecView: RecyclerView? = null
    var allDishAdapter: AllDishesAdapter? = null
    var allDishGridAdapter: AllDishesGridAdapter? = null
    var allDishedViewModel: AllDishesViewModel? = null
    var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_dishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        intToolbar()
        loader_all_dishes.visibility = View.VISIBLE
        controlButtonsAtVerView()
        allDishedViewModel = ViewModelProviders.of(this).get(AllDishesViewModel::class.java)

        intiAllDishRecycleViewAndAdapter()
        fillVerAdap()

        ver_l_bt.setOnClickListener {
            controlButtonsAtVerView()
            intiAllDishRecycleViewAndAdapter()
        }
        grid_l_bt.setOnClickListener {
            controlButtonsAtGridView()
            intiGridAllDishRecycleViewAndAdapter()
            allDishGridAdapter!!.setClickListener(onClickedG)
        }
        allDishAdapter!!.setClickListener(onClicked)
        dishList.clear()
    }

    private fun intToolbar() {
        val toolbar = allDish_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "AlL Dishes"
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

            val action = AllDishesFragmentDirections.actionAllDishesFragmentToDishDetailsFragment(
                dishDes!!,
                dishType!!,
                dishImg!!,
                dishName!!,
                dishPrice!!
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

            val action3 = AllDishesFragmentDirections.actionAllDishesFragmentToDishDetailsFragment(
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
            allDishRecView = all_dishes_rv
            allDishRecView!!.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            allDishAdapter = AllDishesAdapter(dishList, requireContext(),onClicked)
            allDishRecView!!.adapter = allDishAdapter
        }

        private fun fillVerAdap() {
            allDishedViewModel!!.getDishLiveData().observe(viewLifecycleOwner,
                { dishModel ->
                    observerPro()
                    dishList.add(dishModel)
                    allDishAdapter!!.notifyDataSetChanged()
//                allDishAdapter!!.setData(it as ArrayList<DishModel>)
                })
        }

        private fun intiGridAllDishRecycleViewAndAdapter() {
            allDishRecView = all_dishes_rv
            val gridLayoutManager = GridLayoutManager(context, 2)
            allDishRecView!!.layoutManager = gridLayoutManager
            allDishGridAdapter = AllDishesGridAdapter(dishList, requireContext(),onClickedG)
            allDishRecView!!.adapter = allDishGridAdapter
        }

        private fun observerPro() {
            allDishedViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
                { progressObserver ->
                    if (progressObserver) {
                        loader_all_dishes.visibility = View.VISIBLE
                    } else {
                        loader_all_dishes.visibility = View.INVISIBLE
                    }
                })
        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == R.id.menu_my_cart) {
            view?.findNavController()?.navigate(R.id.action_allDishesFragment_to_myCartFragment)
            }
            return true
        }


        private fun controlButtonsAtVerView() {
            ver_d_bt!!.visibility = View.VISIBLE
            ver_l_bt!!.visibility = View.INVISIBLE
            grid_d_bt!!.visibility = View.INVISIBLE
            grid_l_bt!!.visibility = View.VISIBLE
        }

        private fun controlButtonsAtGridView() {
            grid_d_bt!!.visibility = View.VISIBLE
            grid_l_bt!!.visibility = View.INVISIBLE
            ver_d_bt!!.visibility = View.INVISIBLE
            ver_l_bt!!.visibility = View.VISIBLE
        }


    }