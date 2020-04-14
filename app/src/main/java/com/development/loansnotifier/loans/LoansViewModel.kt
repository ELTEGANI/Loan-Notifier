package com.development.loansnotifier.loans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.development.loansnotifier.data.DefaultLoansRepository
import com.development.loansnotifier.data.Loans
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoansViewModel(application: Application) : AndroidViewModel(application) {
    private val loansRepository = DefaultLoansRepository.getRepository(application)


    val loanItems: LiveData<List<Loans>> = loansRepository.getLoans()




}
