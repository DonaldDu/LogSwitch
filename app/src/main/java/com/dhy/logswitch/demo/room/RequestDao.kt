package com.dhy.logswitch.demo.room

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface RequestDao {
    @Insert
    fun insert(log: RequestLog)

    @Query("SELECT * FROM NetLog")
    fun getAllByDataSource(): DataSource.Factory<Int, RequestLog>
}

@Database(entities = [RequestLog::class], version = 1, exportSchema = false)
abstract class NetLogDatabase : RoomDatabase() {
    abstract fun requestDao(): RequestDao
}