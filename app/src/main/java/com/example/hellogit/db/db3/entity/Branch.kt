package com.example.hellogit.db.db3.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Branch")
data class Branch(

    @ColumnInfo(name="repo_id")
    var repo_id:Int,
    @ColumnInfo(name="owner")
    var owner:String,
    @ColumnInfo(name="repo_name")
    var repo_name:String,

    @ColumnInfo(name="branchName")
    var branchName:String,
    @ColumnInfo(name="sha")
    var sha:String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
