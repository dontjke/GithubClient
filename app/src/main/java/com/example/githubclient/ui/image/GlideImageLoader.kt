package com.example.githubclient.ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.githubclient.R
import com.example.githubclient.mvp.view.IImageLoader

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .placeholder(R.drawable.baseline_person_outline_24)
            .into(container)
    }
}