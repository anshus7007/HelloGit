package com.example.hellogit.GitViewModel

import androidx.lifecycle.ViewModel
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitRepository.CommitRepository
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.db.db4.entity.CommitsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommitViewModel (private val repository: CommitRepository): ViewModel() {
    fun insert(item: CommitsEntity)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }
    fun delete(item: CommitsEntity)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllCommits(repoId:Int,branchId:Int) = repository.getAllCommits(repoId,branchId);


}