package com.development.loansnotifier.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.development.loansnotifier.data.local.LoansDatabase
import com.development.loansnotifier.data.local.LoansLocalDataSource
import com.development.loansnotifier.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class DefaultLoansRepository constructor(private val loansDataSource: LoansDataSource,
                                                    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        @Volatile
        private var INSTANCE: DefaultLoansRepository? = null

        fun getRepository(app: Application): DefaultLoansRepository {
            return INSTANCE ?: synchronized(this) {
            val database = Room.databaseBuilder(app,
            LoansDatabase::class.java, "Loans.db")
            .build()
            DefaultLoansRepository(LoansLocalDataSource(database.loanDao())).also {
                    INSTANCE = it
                }
            }
        }
    }


    suspend fun saveLoan(loan: Loans) {
        coroutineScope {
            launch {
                loansDataSource.saveLoans(loan)
            }
        }
    }

    fun getLoans(): LiveData<Result<List<Loans>>> {
        return loansDataSource.getLoans()
    }

    suspend fun paymentLoan(loan: Loans) {
        coroutineScope {
            launch { loansDataSource.paymentLoan(loan) }
        }
    }

    suspend fun activateLoan(loan: Loans){
        coroutineScope {
            launch { loansDataSource.activateLoan(loan) }
        }
    }

}