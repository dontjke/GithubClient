package com.example.githubclient.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.mvp.model.entity.room.RoomGithubUserRepositories
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: RoomGithubUserRepositories): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repository: RoomGithubUserRepositories): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: List<RoomGithubUserRepositories>): Completable

    @Update
    fun update(repository: RoomGithubUserRepositories)

    @Update
    fun update(vararg repository: RoomGithubUserRepositories)

    @Update
    fun update(repository: List<RoomGithubUserRepositories>)

    @Delete
    fun delete(repository: RoomGithubUserRepositories)

    @Delete
    fun delete(vararg repository: RoomGithubUserRepositories)

    @Delete
    fun delete(repository: List<RoomGithubUserRepositories>)

    @Query("SELECT * FROM RoomGithubUserRepositories")
    fun getAll(): Single<List<RoomGithubUserRepositories>>

    @Query("SELECT * FROM RoomGithubUserRepositories WHERE userId = :userId")
    fun findForUser(userId: String): Single<List<RoomGithubUserRepositories>>

}