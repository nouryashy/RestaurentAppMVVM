package com.example.restaurentappmvvm.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.restaurentappmvvm.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.fragment_payment.*
import org.json.JSONObject
import java.lang.Exception


class PaymentFragment : Fragment(), PaymentResultListener {

    var actionBar: ActionBar? = null
    var amount: Double? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PaymentFragmentArgs by navArgs()
        amount = args.totalPriceAmount.toDouble()

        intToolbar()
        sub_total.text = amount.toString()

        pay_btn.setOnClickListener {

            val checkOut = Checkout()
            try {
                val options = JSONObject()
                options.put("name", "Restaurant Task")
                options.put("description", "Reference No. #123456")
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency", "USD")
                amount = amount!! * 100
                options.put("amount", amount)
                val preFill = JSONObject()
                preFill.put("email", "developer.kharag@gmail.com")
                preFill.put("contact", "7489347378")
                options.put("prefill", preFill)
                checkOut.open(activity, options)

            } catch (e: Exception) {

                Log.e("TAG", "Error in starting Razorpay Checkout", e)
            }
        }


    }
    fun intToolbar() {
        val toolbar = payment_toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = " Payment"
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(context, "Payment Cancel", Toast.LENGTH_SHORT).show()
    }

}