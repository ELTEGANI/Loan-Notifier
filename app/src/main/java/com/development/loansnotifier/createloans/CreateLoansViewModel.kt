package com.development.loansnotifier.createloans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.development.loansnotifier.Event
import com.development.loansnotifier.R
import com.development.loansnotifier.data.DefaultLoansRepository
import com.development.loansnotifier.data.Loans
import kotlinx.coroutines.launch

class CreateLoansViewModel(application: Application) : AndroidViewModel(application) {

    private val loansRepository = DefaultLoansRepository.getRepository(application)

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _loanUpdatedEvent = MutableLiveData<Event<Unit>>()
    val loanUpdatedEvent: LiveData<Event<Unit>> = _loanUpdatedEvent

    val amount = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val loanDate = MutableLiveData<String>()

    val paymentDate = MutableLiveData<String>()

    var loanStatus = MutableLiveData<Int>()

    var status = ""

    fun saveLoan() {
              val loanAmount = amount.value
              val loanDescription = description.value
              val loanDate = loanDate.value
              val loanPayment = paymentDate.value
              when (loanStatus.value) {
              R.id.creditor_radio_button -> {
                status = "Creditor"
              }
              R.id.debitor_radio_button -> {
                status = "Debtor"
              }
                   }
     if (loanAmount == null || loanDescription == null || loanDate == null || loanPayment == null) {
         _snackbarText.value = Event(R.string.loan_cant_be_empty)
         return
     } else if (Loans(loanAmount, loanDescription, loanPayment, loanDate, status).isEmpty) {
            _snackbarText.value = Event(R.string.loan_cant_be_empty)
            return
     } else {
         createLoan(Loans(loanAmount, loanDescription, loanPayment, loanDate, status))
     }
        }

    private fun createLoan(loan: Loans) = viewModelScope.launch {
        loansRepository.saveLoan(loan)
        _loanUpdatedEvent.value = Event(Unit)
    }
}
