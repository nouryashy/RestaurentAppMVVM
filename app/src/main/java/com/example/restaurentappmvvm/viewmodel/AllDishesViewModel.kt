package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.DishModel
import com.example.restaurentappmvvm.repository.AllDishesRepository

class AllDishesViewModel(application: Application) : AndroidViewModel(application) {
    private var allDishesRepository = AllDishesRepository(application)
    private var dishLiveData = allDishesRepository.getDishLiveData()
    private var progressbarObservable = allDishesRepository.getProgressLiveData()
    private var scrollObservable = allDishesRepository.getScrollLiveData()


    fun getDishLiveData(): MutableLiveData<DishModel> {
        return dishLiveData
    }
    fun getDishByCateLiveData(type:String): MutableLiveData<DishModel> {
        return allDishesRepository.getDishByCateLiveData(type)
    }
    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }

    fun getScrollLiveData(): MutableLiveData<Boolean> {
        return scrollObservable
    }


}