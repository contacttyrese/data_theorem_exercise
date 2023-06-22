package com.example.datatheoremexercise.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoggedUrlDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUrl(url: LoggedUrl)
    @Delete
    suspend fun deleteUrl(url: LoggedUrl)
    @Query("SELECT * FROM loggedurl")
    fun getAllUrls(): List<LoggedUrl>
}