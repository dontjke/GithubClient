package com.example.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemUserBinding
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.IImageLoader
import com.example.githubclient.mvp.view.list.IUserItemView
import com.example.githubclient.utils.INVALID_INDEX
import javax.inject.Inject

class UsersRVAdapter(
    private val presenter: IUserListPresenter
) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root), IUserItemView {
        override var pos = INVALID_INDEX
        override fun setLogin(text: String) {
            binding.tvLogin.text = text
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadInto(url, binding.ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val viewHolder = ViewHolder(binding)
        binding.buttonRepositories.setOnClickListener {
            presenter.onRepositoriesClicked(viewHolder)
        }
        binding.buttonFollowers.setOnClickListener {
            presenter.onFollowersClicked(viewHolder)
        }
        return viewHolder
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return presenter.bindView(holder.apply {
            pos = position
        })
    }
}