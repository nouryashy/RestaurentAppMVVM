package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.repository.CategoryRepository

class CategoryViewModel (application: Application) : AndroidViewModel(application) {
    private var categoryRepository = CategoryRepository(application)
    private var cateLiveData = categoryRepository.getCatLiveData()
    private var progressbarObservable = categoryRepository.getProgressLiveData()
    private var scrollObservable = categoryRepository.getScrollLiveData()


    fun getCatLiveData(): MutableLiveData<List<CategoryModel>> {
        return cateLiveData
    }
    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }

    fun getScrollLiveData(): MutableLiveData<Boolean> {
        return scrollObservable
    }


}