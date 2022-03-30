package com.example.hellogit.db.db1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hellogit.db.db1.dao.entity.Git

@Dao
interface GitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Git)

    @Delete
    suspend fun delete(item: Git)

    @Query("SELECT * FROM Git")
    fun getAllRepos(): LiveData<List<Git>>
    @Query("SELECT * FROM Git where id=:repo_id")
    fun getRepoById(repo_id:Int): LiveData<Git>

}