package com.development.loansnotifier.data

import androidx.lifecycle.LiveData


interface LoansDataSource {
    suspend fun saveLoans(loan: Loans)
    fun getLoans(): LiveData<List<Loans>>
}