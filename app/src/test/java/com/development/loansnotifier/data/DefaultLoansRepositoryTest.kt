package com.development.loansnotifier.data

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.*
import org.junit.Before

class DefaultLoansRepositoryTest {

    private var loan1 = Loans("1000","for my kids","2020-01-10","2020-02-20"
    ,"true")
    private val loacalLoan = listOf(loan1).sortedBy { it.id }
    private lateinit var loansLocalDataSource: FakeDataSource
    private lateinit var loansRepository: DefaultLoansRepository

    @Before
    fun createRepository(){
        loansLocalDataSource = FakeDataSource(loacalLoan.toMutableList())
        loansRepository      = DefaultLoansRepository(loansLocalDataSource,Dispatchers.Unconfined)
    }



}