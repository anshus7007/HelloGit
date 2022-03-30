package com.example.hellogit.GitRepository

import com.example.hellogit.db.db2.IssuesDB
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.BranchDB
import com.example.hellogit.db.db3.entity.Branch

class BranchRepository(private val db: BranchDB) {

    suspend fun insert(item: Branch)=db.getBranchDao().insert(item)

    suspend fun delete(item: Branch)= db.getBranchDao().delete(item)

    fun getAllBranches(repoId:Int) = db.getBranchDao().getAllBranch(repoId)
}