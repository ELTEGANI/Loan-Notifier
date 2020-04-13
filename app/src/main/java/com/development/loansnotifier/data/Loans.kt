package com.development.loansnotifier.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "Loans")
data class Loans @JvmOverloads constructor(
    @ColumnInfo(name = "loan_amount") var loanAmount:String,
    @ColumnInfo(name = "loan_description") var loanDescription:String,
    @ColumnInfo(name = "date_of_payment") var paymentDate:String,
    @ColumnInfo(name = "date_of_receiving_the_loan") var loanDate:String,
    @ColumnInfo(name = "loan_status") var loanStatus:String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id:Int = 0
)
{
    val isEmpty
        get() = loanAmount.isEmpty() || loanDescription.isEmpty() || paymentDate.isEmpty() || loanDate.isEmpty() || loanStatus.isEmpty()
}
