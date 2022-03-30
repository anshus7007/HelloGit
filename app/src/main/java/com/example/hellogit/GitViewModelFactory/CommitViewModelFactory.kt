package com.example.hellogit.GitViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitRepository.CommitRepository
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModel.CommitViewModel

@Suppress("UNCHECKED_CAST")

class CommitViewModelFactory (
    private val repository: CommitRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommitViewModel(repository) as T
    }
}