package com.development.loansnotifier.util

import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.development.loansnotifier.data.Loans


@BindingAdapter("loanAmount")
fun TextView.setLoanAmount(loans: Loans){
    loans?.let {
        text = loans.loanAmount
    }
}


@BindingAdapter("loanDescription")
fun TextView.setLoanDescription(loans: Loans){
    loans?.let {
        text = loans.loanDescription
    }
}

