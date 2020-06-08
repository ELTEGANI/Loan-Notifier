package com.development.loansnotifier.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.development.loansnotifier.data.Loans
import com.development.loansnotifier.data.LoansDataSource
import com.development.loansnotifier.util.Result
import com.development.loansnotifier.util.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoansLocalDataSource internal constructor(
    private val loansDao: LoansDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoansDataSource {

    override suspend fun saveLoans(loan: Loans) = withContext(ioDispatcher) {
        loansDao.insertLoan(loan)
    }

    override fun getLoans(): LiveData<Result<List<Loans>>> {
        return loansDao.getLoans().map {
            Success(it)
        }
    }

    override suspend fun paymentLoan(loan: Loans) = withContext(ioDispatcher) {
        loansDao.updateLoanPayment(loan.id, true)
    }

    override suspend fun activateLoan(loan: Loans) = withContext(ioDispatcher) {
        loansDao.updateLoanPayment(loan.id, false)
    }
}
