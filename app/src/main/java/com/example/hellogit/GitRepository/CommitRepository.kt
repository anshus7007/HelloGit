package com.example.hellogit.GitRepository

import com.example.hellogit.db.db3.BranchDB
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.db.db4.CommitsDB
import com.example.hellogit.db.db4.entity.CommitsEntity

class CommitRepository(private val db: CommitsDB) {

    suspend fun insert(item: CommitsEntity)=db.getCommitsDao().insert(item)

    suspend fun delete(item: CommitsEntity)= db.getCommitsDao().delete(item)

    fun getAllCommits(repoId:Int,branchId:Int) = db.getCommitsDao().getAllCommits(repoId,branchId)
}