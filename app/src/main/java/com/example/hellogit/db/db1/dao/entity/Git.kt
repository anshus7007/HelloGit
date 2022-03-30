package com.example.hellogit.db.db1.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Git")
data class Git(
    @PrimaryKey
    var id: Int? = null,
    @ColumnInfo(name="repo_name")
    var repo_name:String,
    @ColumnInfo(name="owner")
    var owner:String,

    @ColumnInfo(name="description")
    var description:String




)
