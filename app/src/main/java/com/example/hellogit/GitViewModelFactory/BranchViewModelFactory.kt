package com.example.hellogit.GitViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModel.IssuesViewModel

@Suppress("UNCHECKED_CAST")

class BranchViewModelFactory(
    private val repository: BranchRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BranchViewModel(repository) as T
    }
}