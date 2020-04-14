package com.development.loansnotifier.loans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.development.loansnotifier.R
import com.development.loansnotifier.databinding.CreateNewLoansFragmentBinding
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
        viewModel.loanItems.observe(viewLifecycleOwner, Observer {
            it.let {
                loansAdapter.submitList(it)
            }
        })
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupFab()
        setListAdapter()
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

    private fun setListAdapter(){
        val loansViewModel = viewDataBinding.viewmodel
        if (loansViewModel != null){
            loansAdapter = LoansAdapter(loansViewModel)
            viewDataBinding.loanList.itemAnimator = DefaultItemAnimator()
            viewDataBinding.loanList.adapter = loansAdapter
        }
     }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

}
