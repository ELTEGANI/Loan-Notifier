package com.development.loansnotifier.loans

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.development.loansnotifier.EventObserver
import com.development.loansnotifier.R
import com.development.loansnotifier.databinding.LoansFragmentBinding
import com.development.loansnotifier.util.setupSnackbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class LoansFragment : Fragment() {


    private val viewModel by viewModels<LoansViewModel>()
    private lateinit var viewDataBinding: LoansFragmentBinding
    private lateinit var loansAdapter: LoansAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.loans_fragment, container, false)
        viewDataBinding = LoansFragmentBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_loan_menu, menu)
            setOnMenuItemClickListener {
                viewModel.setFiltering (
                    when (it.itemId) {
                        R.id.paid -> LoanFilterType.PAID_LOANS
                        R.id.unpaid-> LoanFilterType.NUPAID_LOANS
                        else -> LoanFilterType.ALL_LOANS
                    }
                )
                true
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.loan_menu,menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupNavigation()
        setupFab()
        setListAdapter()
    }


    private fun setupNavigation() {
        viewModel.newLoanEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToCreateNewLoans()
        })
    }

    private fun setupFab(){
        activity?.findViewById<FloatingActionButton>(R.id.add_fab)?.let {
            it.setOnClickListener {
                navigateToCreateNewLoans()
            }
        }
    }

    private fun navigateToCreateNewLoans(){
       val actions = LoansFragmentDirections.actionLoansFragmentToCreateLoansFragment()
       findNavController().navigate(actions)
    }

    private fun setListAdapter() {
        val loansViewModel = viewDataBinding.viewmodel
        if (loansViewModel != null) {
            loansAdapter = LoansAdapter(viewModel)
            viewDataBinding.loanList.itemAnimator = DefaultItemAnimator()
            viewDataBinding.loanList.adapter = loansAdapter
        }
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

}
