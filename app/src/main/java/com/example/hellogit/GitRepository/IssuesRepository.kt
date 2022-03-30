package com.example.hellogit.GitRepository

import com.example.hellogit.db.db2.IssuesDB
import com.example.hellogit.db.db2.entity.Issues

class IssuesRepository(private val db: IssuesDB) {

    suspend fun insert(item: Issues)=db.getIssuesDao().insert(item)

    suspend fun delete(item: Issues)= db.getIssuesDao().delete(item)

    fun getAllIssues(repoId:Int) = db.getIssuesDao().getAllIssues(repoId)
}