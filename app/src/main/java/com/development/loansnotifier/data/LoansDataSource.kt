package com.development.loansnotifier.data

import androidx.lifecycle.LiveData
import com.development.loansnotifier.util.Result

interface LoansDataSource {
    suspend fun saveLoans(loan: Loans)
    fun getLoans(): LiveData<Result<List<Loans>>>
    suspend fun paymentLoan(loan: Loans)
    suspend fun activateLoan(loan: Loans)
}
