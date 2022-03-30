package com.example.hellogit.db.db2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Issues")
data class Issues(
    @PrimaryKey
    var id: Int? = null,
    @ColumnInfo(name="repo_id")
    var repo_id:Int,
    @ColumnInfo(name="title")
    var title:String,
    @ColumnInfo(name="user")
    var user:String,
    @ColumnInfo(name="avatar")
    var avatar:String
)
