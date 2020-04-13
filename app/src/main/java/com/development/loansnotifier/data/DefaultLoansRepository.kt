package com.development.loansnotifier.data

import android.app.Application
import androidx.room.Room
import com.development.loansnotifier.data.local.LoansDatabase
import com.development.loansnotifier.data.local.LoansLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class DefaultLoansRepository private constructor(application: Application) {

    private val loansDataSource: LoansDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    companion object {
        @Volatile
        private var INSTANCE: DefaultLoansRepository? = null

        fun getRepository(app: Application): DefaultLoansRepository {
            return INSTANCE ?: synchronized(this) {
                DefaultLoansRepository(app).also {
                    INSTANCE = it
                }
            }
        }
    }

    init {
        val database = Room.databaseBuilder(application.applicationContext,
            LoansDatabase::class.java, "Loans.db")
            .build()
        loansDataSource  = LoansLocalDataSource(database.loanDao())
    }

    suspend fun saveLoan(loan: Loans) {
        coroutineScope {
            launch {
                loansDataSource.saveLoans(loan)
            }
        }
    }

}