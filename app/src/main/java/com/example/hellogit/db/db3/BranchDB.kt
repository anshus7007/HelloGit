package com.example.hellogit.db.db3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hellogit.db.db2.dao.IssuesDao
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.dao.BranchDao
import com.example.hellogit.db.db3.entity.Branch

@Database(
    entities = [Branch::class],
    version = 1)
abstract class BranchDB: RoomDatabase() {
    abstract fun getBranchDao() : BranchDao

    //static in java
    //creates single instance at a time
    //database class is used at different places in app so there should only be one call from any part of app
    //therefore companion object is used
    companion object{
        @Volatile
        private var instance: BranchDB?  = null

        private val LOCK = Any()
        //val db=CollectMoneyDatabase(this)::::invoke function will be called
        //if instance is null it'll create a new instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            //if instance is null createDatabase will be called
            instance ?: createDatabase(context).also {
                //result returned from createDatabase is stored in it
                instance =it
            }

        }
        //new instance will be create of this database class and result will be returned
        private  fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext, BranchDB::class.java,
                "BranchDB.db").build()
    }

}


