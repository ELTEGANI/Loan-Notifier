package com.development.loansnotifier.loans

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.development.loansnotifier.Event
import com.development.loansnotifier.R
import com.development.loansnotifier.data.DefaultLoansRepository
import com.development.loansnotifier.data.Loans
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoansViewModel(application: Application) : AndroidViewModel(application) {
    private val loansRepository = DefaultLoansRepository.getRepository(application)

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    val loanItems: LiveData<List<Loans>> = loansRepository.getLoans()

    fun paymentLoan(loans: Loans, isPayment: Boolean) = viewModelScope.launch {
        if (isPayment) {
            loansRepository.paymentLoan(loans)
            showSnackbarMessage(R.string.payment_marked_done)
        } else {
            loansRepository.activateLoan(loans)
            showSnackbarMessage(R.string.marked_active)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}
