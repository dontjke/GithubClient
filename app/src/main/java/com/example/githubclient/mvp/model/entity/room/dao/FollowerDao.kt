package com.example.githubclient.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.mvp.model.entity.room.RoomGithubFollower
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface FollowerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(follower: RoomGithubFollower): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg followers: RoomGithubFollower): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(followers: List<RoomGithubFollower>): Completable

    @Update
    fun update(follower: RoomGithubFollower)

    @Update
    fun update(vararg followers: RoomGithubFollower)

    @Update
    fun update(followers: List<RoomGithubFollower>)

    @Delete
    fun delete(follower: RoomGithubFollower)

    @Delete
    fun delete(vararg followers: RoomGithubFollower)

    @Delete
    fun delete(followers: List<RoomGithubFollower>)

    @Query("SELECT * FROM RoomGithubFollower")
    fun getAll(): Single<List<RoomGithubFollower>>

    @Query("SELECT * FROM RoomGithubFollower WHERE userId = :userId")
    fun findForUser(userId: String): Single<List<RoomGithubFollower>>
}