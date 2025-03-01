package com.example.finwise.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val name: String,
    val mobileNumber: String,
)
