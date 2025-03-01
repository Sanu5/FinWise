package com.example.finwise

import androidx.lifecycle.liveData
import com.example.finwise.database.User
import com.example.finwise.database.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun insertUser(user: User)  {
        userDao.insertUser(user)
    }
}