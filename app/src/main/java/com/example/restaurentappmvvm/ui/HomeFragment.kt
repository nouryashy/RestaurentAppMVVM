package com.example.restaurentappmvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.adapter.CategoryAdapter
import com.example.restaurentappmvvm.adapter.PopularDishesAdapter
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.DishModel
import com.example.restaurentappmvvm.viewmodel.AllDishesViewModel
import com.example.restaurentappmvvm.viewmodel.CategoryViewModel
import com.example.restaurentappmvvm.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.category_item_list.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    var categoryList = ArrayList<CategoryModel>()
    var catRecView: RecyclerView? = null
    var catAdapter: CategoryAdapter? = null
    var dishList = ArrayList<DishModel>()
    var popDishRecView: RecyclerView? = null
    var popDishAdapter: PopularDishesAdapter? = null
    var actionBar: ActionBar? = null
    var signInViewMode: SignInViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    var allDishedViewModel: AllDishesViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intToolbar()
        setHasOptionsMenu(true)
        toggleViews(View.VISIBLE, View.VISIBLE, View.VISIBLE)
        scrollView.visibility = View.INVISIBLE
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        allDishedViewModel = ViewModelProviders.of(this).get(AllDishesViewModel::class.java)
        intiCatRecycleViewAndAdapter()
        intiPopRecycleViewAndAdapter()

        signInViewMode = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        signInViewMode!!.getLoggedOutLiveData().observe(viewLifecycleOwner,
            { loggedOut ->
                if (loggedOut) {
                    Toast.makeText(context, "User Logged Out", Toast.LENGTH_SHORT).show();
                }
            })
        See_all_label_tv.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_allDishesFragment)
        }

        catAdapter!!.setClickListener(onClicked)
        popDishAdapter!!.setClickListener(onClicked1)


    }


    private fun intToolbar() {
        val toolbar = home_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
//        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "Home"
    }


    private val onClicked = object : CategoryAdapter.OnItemClickListener {
        override fun onClicked(type: String) {
            val action = HomeFragmentDirections.actionHomeFragmentToCatDishFragment(type)
            findNavController().navigate(action)
        }

    }
    private val onClicked1 = object : PopularDishesAdapter.OnItemClickListener {
        override fun onClicked1(dishModel: DishModel) {
            val dishName = dishModel.dish_title
            val dishDes = dishModel.dish_description
            val dishPrice = dishModel.price
            val dishImg = dishModel.dish_img
            val dishType = dishModel.type
            val action1 = HomeFragmentDirections.actionHomeFragmentToDishDetailsFragment(
                dishDes!!,
                dishType!!,
                dishPrice!!,
                dishName!!,
                dishImg!!
            )
            findNavController().navigate(action1)
        }
    }

    private fun intiCatRecycleViewAndAdapter() {
        catRecView = cat_rv
        catRecView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        catAdapter = CategoryAdapter(categoryList, requireContext(), onClicked)
        catRecView!!.adapter = catAdapter
        categoryViewModel!!.getCatLiveData().observe(viewLifecycleOwner,
            {
                observerPro()
                observerScroll()
//                categoryList.add(categoryModel)
//                catAdapter!!.notifyDataSetChanged()
                catAdapter!!.setData(it as ArrayList<CategoryModel>)
            })
    }

    private fun intiPopRecycleViewAndAdapter() {
        popDishRecView = pop_dish_rv
        popDishRecView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        popDishAdapter = PopularDishesAdapter(dishList, requireContext(),onClicked1)
        popDishRecView!!.adapter = popDishAdapter
//        dishList.clear()
        allDishedViewModel!!.getDishLiveData().observe(viewLifecycleOwner,
            { dishModel ->
                observerPro()
                observerScroll()
                dishList.add(dishModel)
                popDishAdapter!!.notifyDataSetChanged()
//                popDishAdapter!!.setData(it as ArrayList<DishModel>)
            })
    }


    private fun observerPro() {
        categoryViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
            { progressObserver ->
                if (progressObserver) {
                    toggleViews(View.VISIBLE, View.VISIBLE, View.VISIBLE)
                } else {
                    toggleViews(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE)
                }
            })
    }

    private fun observerScroll() {
        categoryViewModel!!.getScrollLiveData().observe(viewLifecycleOwner,
            { scrollObserver ->
                if (scrollObserver) {
                    scrollView.visibility = View.VISIBLE
                } else {
                    scrollView.visibility = View.INVISIBLE
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_logout) {
            signInViewMode!!.logOut()
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_signInFragment)
        } else if (id == R.id.menu_my_cart) {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_myCartFragment)
        }
        return true
    }

    private fun toggleViews(loaderCat: Int, loaderPopRec: Int, loaderScroll: Int) {
        loader_cat.visibility = loaderCat
        loader_pop_dishes.visibility = loaderPopRec
        loader_Scroll_View.visibility = loaderScroll
    }


}