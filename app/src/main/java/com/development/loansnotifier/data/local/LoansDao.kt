package com.development.loansnotifier.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.development.loansnotifier.data.Loans


@Dao
interface LoansDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoan(loan: Loans)

    @Query("SELECT * FROM Loans")
    fun getLoans(): LiveData<List<Loans>>
}