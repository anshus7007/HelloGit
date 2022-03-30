package com.example.hellogit.GitViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModel.IssuesViewModel
@Suppress("UNCHECKED_CAST")

class IssuesViewModelFactory(
    private val repository: IssuesRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IssuesViewModel(repository) as T
    }
}