package com.example.githubclient.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.mvp.model.entity.room.RoomGithubUserRepositories

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubUserRepositories)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGithubUserRepositories)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubUserRepositories>)

    @Update
    fun update(user: RoomGithubUserRepositories)

    @Update
    fun update(vararg users: RoomGithubUserRepositories)

    @Update
    fun update(users: List<RoomGithubUserRepositories>)

    @Delete
    fun delete(user: RoomGithubUserRepositories)

    @Delete
    fun delete(vararg users: RoomGithubUserRepositories)

    @Delete
    fun delete(users: List<RoomGithubUserRepositories>)

    @Query("SELECT * FROM RoomGithubUserRepositories")
    fun getAll(): List<RoomGithubUserRepositories>

    @Query("SELECT * FROM RoomGithubUserRepositories WHERE userId = :userId")
    fun findForUser(userId: String): List<RoomGithubUserRepositories>

}