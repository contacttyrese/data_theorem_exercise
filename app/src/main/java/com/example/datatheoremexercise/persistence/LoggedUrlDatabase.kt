package com.example.datatheoremexercise.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LoggedUrl::class],
    version = 1
)
abstract class LoggedUrlDatabase : RoomDatabase() {
    abstract val dao: LoggedUrlDao
}