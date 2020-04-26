package com.development.loansnotifier.loans

import android.app.Application
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.development.loansnotifier.Event
import com.development.loansnotifier.R
import com.development.loansnotifier.data.DefaultLoansRepository
import com.development.loansnotifier.data.Loans
import com.development.loansnotifier.loans.LoanFilterType.*
import com.development.loansnotifier.util.Result
import com.development.loansnotifier.util.Result.Success
import kotlinx.coroutines.launch


class LoansViewModel(application: Application) : AndroidViewModel(application) {
    private val loansRepository = DefaultLoansRepository.getRepository(application)

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    val loanItems: LiveData<List<Loans>>  = _forceUpdate.switchMap {
        loansRepository.getLoans().switchMap { filterLoans(it) }
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()

    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private var currentFiltering = ALL_LOANS


    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private val _noLoansLabel = MutableLiveData<Int>()
    val noLoansLabel: LiveData<Int> = _noLoansLabel

    private val _noLoanIconRes = MutableLiveData<Int>()
    val noLoanIconRes: LiveData<Int> = _noLoanIconRes

    private val _loansAddViewVisible = MutableLiveData<Boolean>()
    val loansAddViewVisible: LiveData<Boolean> = _loansAddViewVisible

    private val _newLoanEvent = MutableLiveData<Event<Unit>>()
    val newLoanEvent: LiveData<Event<Unit>> = _newLoanEvent


    val empty: LiveData<Boolean> = Transformations.map(loanItems) {
        it.isEmpty()
    }

    init {
        setFiltering(ALL_LOANS)
        loadLoans(true)
    }

    fun loadLoans(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun setFiltering(requestType: LoanFilterType) {
        currentFiltering = requestType
        when (requestType) {
            ALL_LOANS -> {
                setFilter(
                    R.string.all_loan,R.string.no_loans,
                    R.drawable.logo_no_fill, false
                )
            }
            PAID_LOANS -> {
                setFilter(
                    R.string.label_paid,R.string.no_payed_loan,
                    R.drawable.ic_check_circle_96dp, false
                )
            }
            NUPAID_LOANS -> {
                setFilter(
                    R.string.label_unpaid,R.string.no_unpaid_loan,
                    R.drawable.ic_verified_user_96dp, false
                )
            }
        }
        loadLoans(false)
    }

    private fun setFilter(
        @StringRes filteringLabelString: Int, @StringRes noLoansLabelString: Int,
        @DrawableRes noLoanIconDrawable: Int, loansAddVisible: Boolean
    ) {
        _currentFilteringLabel.value = filteringLabelString
        _noLoansLabel.value = noLoansLabelString
        _noLoanIconRes.value = noLoanIconDrawable
        _loansAddViewVisible.value = loansAddVisible
    }

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


    private fun filterLoans(loansResult: Result<List<Loans>>): LiveData<List<Loans>> {
        val result = MutableLiveData<List<Loans>>()
        if (loansResult is Success) {
            viewModelScope.launch {
                result.value = filterItems(loansResult.data, currentFiltering)
            }
        } else {
            result.value = emptyList()
        }
        Log.d("result",result.toString())
        return result
    }

    private fun filterItems(loans: List<Loans>,loanFilterType: LoanFilterType): List<Loans> {
        val loansToShow = ArrayList<Loans>()
        for (loan in loans) {
            when (loanFilterType) {
                ALL_LOANS -> loansToShow.add(loan)
                PAID_LOANS -> if (loan.isPaid){
                    loansToShow.add(loan)
                }
                NUPAID_LOANS ->if (loan.isNotPaid){
                    loansToShow.add(loan)
                }
            }
        }
        return loansToShow
    }

    fun addNewLoan() {
        _newLoanEvent.value = Event(Unit)
    }

}
