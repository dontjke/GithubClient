package com.example.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemFollowerBinding
import com.example.githubclient.mvp.presenter.list.IFollowersListPresenter
import com.example.githubclient.mvp.view.IImageLoader
import com.example.githubclient.mvp.view.list.IFollowerItemView
import com.example.githubclient.utils.INVALID_INDEX
import javax.inject.Inject

class FollowersRVAdapter(
    private val presenter: IFollowersListPresenter
) :
    RecyclerView.Adapter<FollowersRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(private val binding: ItemFollowerBinding) :
        RecyclerView.ViewHolder(binding.root), IFollowerItemView {
        override var pos = INVALID_INDEX
        override fun setLogin(text: String) {
            binding.tvLoginFollower.text = text
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadInto(url, binding.ivAvatarFollower)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFollowerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
        })

    override fun getItemCount() = presenter.getCount()
}