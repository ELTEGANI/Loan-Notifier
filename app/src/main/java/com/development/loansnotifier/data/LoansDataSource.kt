package com.development.loansnotifier.data



interface LoansDataSource {
    suspend fun saveLoans(loan: Loans)
}