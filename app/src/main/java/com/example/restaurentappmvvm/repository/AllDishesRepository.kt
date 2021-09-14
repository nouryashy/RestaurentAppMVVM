package com.example.restaurentappmvvm.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.model.CategoryModel
import com.example.restaurentappmvvm.model.DishModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_cat_dish.*


class AllDishesRepository(application: Application) {
    private var application = Application()
    private var dishLiveData = MutableLiveData<DishModel>()
    private var dishList = ArrayList<DishModel>()
    private var dishCateLiveData = MutableLiveData<DishModel>()
    private var db = FirebaseFirestore.getInstance()
    private var dishModel = DishModel()
    private var progressbarObservable = MutableLiveData<Boolean>()
    private var scrollObservable = MutableLiveData<Boolean>()


    fun getDishLiveData(): MutableLiveData<DishModel> {
        db.collection("All_Dishes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressbarObservable.value = false
                    scrollObservable.value = true
                    for (document in task.result!!) {
                        dishModel = document.toObject(DishModel::class.java)
//                        dishList.add(dishModel)
//                        dishLiveData.value = (dishList)
                        dishLiveData.value = (dishModel)



                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
        return dishLiveData
    }

    fun getDishByCateLiveData(type: String): MutableLiveData<DishModel> {
        if (type == null || type.isEmpty()) {
            db.collection("All_Dishes")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }
                }

        } else if (type != null && type.equals("Chicken")) {
            db.collection("All_Dishes").whereEqualTo("type", "Chicken")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else if (type != null && type.equals("Beef")) {
            db.collection("All_Dishes").whereEqualTo("type", "Beef")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else if (type != null && type.equals("Sandwiches")) {
            db.collection("All_Dishes").whereEqualTo("type", "Sandwiches")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else if (type != null && type.equals("Pizza")) {
            db.collection("All_Dishes").whereEqualTo("type", "Pizza")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else if (type != null && type.equals("Sea food")) {
            db.collection("All_Dishes").whereEqualTo("type", "Sea food")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else if (type != null && type.equals("Dessert")) {
            db.collection("All_Dishes").whereEqualTo("type", "Dessert")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        loader_cat_dishes.visibility = View.INVISIBLE
                        progressbarObservable.value = false
                        for (document in task.result!!) {
                            dishModel = document.toObject(DishModel::class.java)
                            dishCateLiveData.value = dishModel

                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)

                    }

                }

        } else {
            Toast.makeText(application, " Some thing wrong", Toast.LENGTH_SHORT).show()
        }

        return dishCateLiveData
    }


    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }

    fun getScrollLiveData(): MutableLiveData<Boolean> {
        return scrollObservable
    }
}