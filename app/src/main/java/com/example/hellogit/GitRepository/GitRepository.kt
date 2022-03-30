package com.example.hellogit.GitRepository


import com.example.hellogit.db.db1.dao.GitDB
import com.example.hellogit.db.db1.dao.entity.Git

class GitRepository(private val db: GitDB) {

    suspend fun insert(item: Git)=db.getGitDao().insert(item)

    suspend fun delete(item: Git)= db.getGitDao().delete(item)

    fun getAllRepos() = db.getGitDao().getAllRepos()
    fun getRepoById(repoId:Int) = db.getGitDao().getRepoById(repoId)

}