package com.example.restaurentappmvvm.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class CategoryRepository(application: Application) {
    private var application = Application()
    private var cateLiveData = MutableLiveData<List<CategoryModel>>()
    private var db = FirebaseFirestore.getInstance()
    private var categoryList=ArrayList<CategoryModel>()
    private var  categoryModel= CategoryModel()
    private var progressbarObservable = MutableLiveData<Boolean>()
    private var scrollObservable = MutableLiveData<Boolean>()

    fun getCatLiveData(): MutableLiveData<List<CategoryModel>> {
        db.collection("Category")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressbarObservable.value = false
                    scrollObservable.value=true
                    for (document in task.result!!) {
                        categoryModel = document.toObject(CategoryModel::class.java)
                        categoryList.add(categoryModel)
                        cateLiveData.value=(categoryList)

                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
        return cateLiveData
    }

    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }
    fun getScrollLiveData(): MutableLiveData<Boolean> {
        return scrollObservable
    }


}
