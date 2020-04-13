package com.development.loansnotifier.loans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.development.loansnotifier.R
import com.development.loansnotifier.databinding.LoansFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoansFragment : Fragment() {


    private val viewModel by viewModels<LoansViewModel>()
    private lateinit var viewDataBinding: LoansFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewDataBinding = LoansFragmentBinding.inflate(inflater, container,
            false).apply {
            viewModel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupFab()
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

}
