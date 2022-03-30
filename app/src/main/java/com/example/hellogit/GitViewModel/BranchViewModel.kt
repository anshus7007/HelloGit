package com.example.hellogit.GitViewModel

import androidx.lifecycle.ViewModel
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.entity.Branch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BranchViewModel(private val repository: BranchRepository): ViewModel() {
    fun insert(item: Branch)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }
    fun delete(item: Branch)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllBranches(repoId:Int) = repository.getAllBranches(repoId);


}