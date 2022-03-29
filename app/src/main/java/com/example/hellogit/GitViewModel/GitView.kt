package com.example.hellogit.GitViewModel

import androidx.lifecycle.ViewModel

import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.db.entity.Git
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitView(private val repository: GitRepository):ViewModel() {
    fun insert(item: Git)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }
    fun delete(item: Git)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllRepos() = repository.getAllRepos();


}