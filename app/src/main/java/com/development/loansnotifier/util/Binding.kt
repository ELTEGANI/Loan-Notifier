package com.development.loansnotifier.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.loansnotifier.data.Loans
import com.development.loansnotifier.loans.LoansAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Loans>?) {
    items?.let {
        (listView.adapter as LoansAdapter).submitList(items)
    }
}

@BindingAdapter("loanAmount")
fun TextView.setLoanAmount(loans: Loans) {
    loans.let {
        text = loans.loanAmount
    }
}

@BindingAdapter("loanDescription")
fun TextView.setLoanDescription(loans: Loans) {
    loans.let {
        text = loans.loanDescription
    }
}
