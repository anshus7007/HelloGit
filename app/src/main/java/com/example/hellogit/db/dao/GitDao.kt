package com.example.hellogit.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hellogit.db.entity.Git

@Dao
interface GitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Git)

    @Delete
    suspend fun delete(item: Git)

    @Query("SELECT * FROM Git")
    fun getAllRepos(): LiveData<List<Git>>
}