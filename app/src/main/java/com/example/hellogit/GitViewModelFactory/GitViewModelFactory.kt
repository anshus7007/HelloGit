package com.example.hellogit.GitViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.GitView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class GitViewModelFactory(
    private val repository: GitRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitView(repository) as T
    }
}