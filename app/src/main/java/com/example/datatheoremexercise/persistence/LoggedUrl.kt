package com.example.datatheoremexercise.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoggedUrl(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String
)