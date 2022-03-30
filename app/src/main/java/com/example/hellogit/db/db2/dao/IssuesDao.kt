package com.example.hellogit.db.db2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db1.dao.entity.Git

@Dao
interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Issues)

    @Delete
    suspend fun delete(item: Issues)

    @Query("SELECT * FROM Issues where repo_id=:repoId")
    fun getAllIssues(repoId:Int): LiveData<List<Issues>>
}