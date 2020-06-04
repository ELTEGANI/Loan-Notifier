package com.development.loansnotifier.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.development.loansnotifier.data.Loans

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Loans::class], version = 1, exportSchema = false)
abstract class LoansDatabase : RoomDatabase() {

    abstract fun loanDao(): LoansDao
}
