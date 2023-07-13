package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubFollower(
    @Expose val id: String,
    @Expose val login: String,
    @Expose val avatarUrl: String? = null
) : Parcelable