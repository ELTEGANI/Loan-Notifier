package com.development.loansnotifier.data.local

import com.development.loansnotifier.data.Loans
import com.development.loansnotifier.data.LoansDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoansLocalDataSource internal constructor(
    private val loansDao: LoansDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoansDataSource {




    override suspend fun saveLoans(loan: Loans) = withContext(ioDispatcher){
        loansDao.insertLoan(loan)
    }

}