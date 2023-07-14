package com.example.githubclient.mvp.model.entity.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubclient.mvp.model.entity.room.dao.FollowerDao
import com.example.githubclient.mvp.model.entity.room.dao.RepositoryDao
import com.example.githubclient.mvp.model.entity.room.dao.UserDao

@androidx.room.Database(
    entities = [RoomGithubUser::class, RoomGithubUserRepositories::class, RoomGithubFollower::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
    abstract val followerDao: FollowerDao

    companion object {
        const val DB_NAME = "database.db"

    }
}