package com.example.hellogit.GitViewModel

import androidx.lifecycle.ViewModel
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db1.dao.entity.Git
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssuesViewModel(private val repository: IssuesRepository): ViewModel() {
    fun insert(item: Issues)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }
    fun delete(item: Issues)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllIssues(repoId:Int) = repository.getAllIssues(repoId);


}