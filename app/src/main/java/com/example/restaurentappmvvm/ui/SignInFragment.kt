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
import com.example.restaurentappmvvm.viewmodel.SignInViewModel
import com.example.restaurentappmvvm.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignInFragment : Fragment() {

    private var email: String? = null
    private var password: String? = null
    private var signInViewModel:SignInViewModel?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader_login.visibility = View.INVISIBLE
        sign_in_btn.visibility = View.VISIBLE

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        sign_in_tv2.setOnClickListener {
            view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        sign_in_btn.setOnClickListener {
            email = email_et_si.text.toString()
            password = pass_et_si.text.toString()

            when {
                TextUtils.isEmpty(email) -> {
                    email_et_si.error = "Please enter  your Email"
                    email_et_si.requestFocus()
                }
                TextUtils.isEmpty(password) -> {
                    pass_et_si.error = "Please enter  your password"
                    pass_et_si.requestFocus()
                }
                password!!.length < 6 -> {
                    pass_et_si.error = "password too short,enter 6 character minimum !"
                    pass_et_si.requestFocus()
                }
                else -> {
                    signInViewModel!!.signIn(email!!, password!!,view)
                    observerSignInPro()
                    observerBtn()

                }
            }
        }
    }

    private fun observerSignInPro() {
        signInViewModel!!.getProgressLiveData().observe(viewLifecycleOwner,
            { progressObserver ->
                if (progressObserver) {
                    loader_login.visibility = View.VISIBLE
                } else {

                    loader_login.visibility = View.INVISIBLE
                }
            })

    }

    private fun observerBtn() {
        signInViewModel!!.getBtLiveData().observe(viewLifecycleOwner,
            { btnObserve ->
                if (btnObserve) {
                    sign_in_btn.visibility = View.VISIBLE
                } else {

                    sign_in_btn.visibility = View.INVISIBLE
                }
            })
    }

}