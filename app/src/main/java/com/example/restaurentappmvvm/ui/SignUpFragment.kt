package com.example.restaurentappmvvm.ui

import android.os.Bundle

import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.restaurentappmvvm.R
import com.example.restaurentappmvvm.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

    private var userName: String? = null
    private var email: String? = null
    private var password: String? = null

    private var signUpViewModel: SignUpViewModel? = null
    private var firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader_regest.visibility = View.INVISIBLE
        sign_up_btn.visibility = View.VISIBLE
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

//        signUpViewModel!!.getUserLiveDate().observe(viewLifecycleOwner,
//            { firebaseUser ->
//                if (firebaseUser != null) {
//                    Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_homeFragment)
//                }
//            })
        signUpViewModel!!.ifCurrentUserExist(view)

//        if (firebaseAuth.currentUser != null) {
//            view.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
//
//        }

        sign_up_btn.setOnClickListener {
            userName = name_et_su.text.toString()
            email = email_et_su.text.toString()
            password = pass_et_su.text.toString()

            when {
                TextUtils.isEmpty(userName) -> {
                    name_et_su.error = "Please enter your name"
                    name_et_su.requestFocus()

                }
                TextUtils.isEmpty(email) -> {
                    email_et_su.error = "Please enter  your Email"
                    email_et_su.requestFocus()
                }
                TextUtils.isEmpty(password) -> {
                    pass_et_su.error = "Please enter  your password"
                    pass_et_su.requestFocus()
                }
                password!!.length < 6 -> {
                    pass_et_su.error = "password too short,enter 6 character minimum !"
                    pass_et_su.requestFocus()
                }
                else -> {
                    signUpViewModel!!.signUp(email!!, password!!,view)
                    observerSignUpPro()
                    observerBtn()
                }
            }


        }
        sign_up_tv2.setOnClickListener {

            view.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }


    }


    private fun observerSignUpPro() {
        signUpViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
            { progressObserver ->
                if (progressObserver) {
                    loader_regest.visibility = View.VISIBLE
                } else {

                    loader_regest.visibility = View.INVISIBLE
                }
            })

    }

    private fun observerBtn() {
        signUpViewModel!!.getBtLiveData().observe(viewLifecycleOwner,
            { btnObserve ->
                if (btnObserve) {
                    sign_up_btn.visibility = View.VISIBLE
                } else {

                    sign_up_btn.visibility = View.INVISIBLE
                }
            })
    }

}