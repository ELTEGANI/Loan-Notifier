package com.development.loansnotifier.loans

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.development.loansnotifier.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoansViewModelTest {
    @Test
    fun addNewLoan_setNewLoanEvent() {
        // Given fresh ViewModel
        val loanViewModel = LoansViewModel(ApplicationProvider.getApplicationContext())

        // when adding new loan
        loanViewModel.addNewLoan()

        // then the new loan event is triggered
        val value = loanViewModel.newLoanEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }
}
