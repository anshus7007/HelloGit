package com.example.hellogit.db.db4.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Commits")

data class CommitsEntity(
    @ColumnInfo(name="repo_id")
    var repo_id:Int,
    @ColumnInfo(name="branch_id")
    var branch_id:Int,
    @ColumnInfo(name="author")
    var author:String,
    @ColumnInfo(name="date")
    var date:String,
    @ColumnInfo(name="commit_message")
    var commit_message:String,
    @ColumnInfo(name="userName")
    var userName:String,
    @ColumnInfo(name="avatar")
var avatar:String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
