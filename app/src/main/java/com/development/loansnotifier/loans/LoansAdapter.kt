package com.development.loansnotifier.loans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.loansnotifier.data.Loans
import com.development.loansnotifier.databinding.LoanItemsLayoutBinding

class LoansAdapter(private val loansViewModel: LoansViewModel) : ListAdapter<Loans, LoansAdapter.ViewHolder>(
    LoansDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(loansViewModel, getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: LoanItemsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loansViewModel: LoansViewModel, loans: Loans) {
            binding.viewmodel = loansViewModel
            binding.loanItems = loans
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LoanItemsLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class LoansDiffCallback : DiffUtil.ItemCallback<Loans>() {
    override fun areItemsTheSame(oldItem: Loans, newItem: Loans): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Loans, newItem: Loans): Boolean {
        return oldItem == newItem
    }
}
