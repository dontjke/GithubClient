package com.example.githubclient.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubUser): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGithubUser): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubUser>): Completable

    @Update
    fun update(user: RoomGithubUser)

    @Update
    fun update(vararg users: RoomGithubUser)

    @Update
    fun update(users: List<RoomGithubUser>)

    @Delete
    fun delete(user: RoomGithubUser)

    @Delete
    fun delete(vararg users: RoomGithubUser)

    @Delete
    fun delete(users: List<RoomGithubUser>)

    @Query("SELECT * FROM RoomGithubUser")
    fun getAll(): Single<List<RoomGithubUser>>

    @Query("SELECT * FROM RoomGithubUser WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): Single<RoomGithubUser>
}