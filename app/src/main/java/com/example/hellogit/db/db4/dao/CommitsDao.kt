package com.example.hellogit.db.db4.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.db.db4.entity.CommitsEntity

@Dao
interface CommitsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CommitsEntity)

    @Delete
    suspend fun delete(item: CommitsEntity)

    @Query("SELECT * FROM Commits where repo_id=:repoId and branch_id=:branch_id")
    fun getAllCommits(repoId: Int,branch_id:Int): LiveData<List<CommitsEntity>>
}