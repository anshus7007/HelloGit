package com.example.hellogit.db.db3.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.entity.Branch

@Dao
interface BranchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Branch)

    @Delete
    suspend fun delete(item: Branch)

    @Query("SELECT * FROM Branch where repo_id=:repoId")
    fun getAllBranch(repoId: Int): LiveData<List<Branch>>
}
