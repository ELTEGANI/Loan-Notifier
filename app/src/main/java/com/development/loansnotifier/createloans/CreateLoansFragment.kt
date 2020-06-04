package com.development.loansnotifier.createloans

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.development.loansnotifier.EventObserver
import com.development.loansnotifier.R
import com.development.loansnotifier.databinding.CreateNewLoansFragmentBinding
import com.development.loansnotifier.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class CreateLoansFragment : Fragment() {

    private lateinit var createLoansFragmentBinding: CreateNewLoansFragmentBinding
    private val viewModel by viewModels<CreateLoansViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.create_new_loans_fragment, container, false)
        createLoansFragmentBinding = CreateNewLoansFragmentBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        createLoansFragmentBinding.loanDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = context?.let { it1 -> DatePickerDialog(
                it1,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val date = format.format(c.time)
                    viewModel.loanDate.value = date
                }, year, month, day)
            }
            datePickerDialog?.show()
        }

        createLoansFragmentBinding.paymentDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = context?.let { it1 -> DatePickerDialog(
                it1,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val date = format.format(c.time)
                    viewModel.paymentDate.value = date
                }, year, month, day)
            }
            datePickerDialog?.show()
        }

        return createLoansFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createLoansFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupNavigation()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.loanUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            val action = CreateLoansFragmentDirections.actionCreateLoansFragmentToLoansFragment()
            findNavController().navigate(action)
        })
    }
}
