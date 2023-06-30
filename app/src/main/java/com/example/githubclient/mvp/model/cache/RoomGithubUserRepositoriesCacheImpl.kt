package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubUserRepositories
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomGithubUserRepositoriesCacheImpl(private val database: Database) : IRepositoriesCache {
    override fun insertRepositoriesToDatabase(
        githubUserRepositories: List<GithubUserRepositories>,
        githubUser: GithubUser
    ): Completable {
        return database.repositoryDao.insert(githubUserRepositories.map { userRepos ->
            RoomGithubUserRepositories(
                userRepos.id,
                userRepos.name,
                userRepos.forksCount,
                githubUser.id
            )
        })
    }

    override fun getRepositoriesFromDatabase(user: GithubUser): Single<List<GithubUserRepositories>> {
        return database.repositoryDao.findForUser(user.id).map {
            it.map { roomUserRepos ->
                GithubUserRepositories(
                    roomUserRepos.id,
                    roomUserRepos.name,
                    roomUserRepos.forksCount
                )
            }
        }
    }
}