package com.example.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemUserRepositoryBinding
import com.example.githubclient.mvp.presenter.list.IRepositoryListPresenter
import com.example.githubclient.mvp.view.list.IRepositoryItemView
import com.example.githubclient.utils.INVALID_INDEX

class RepositoryRVAdapter(
    private val presenter: IRepositoryListPresenter,
) :
    RecyclerView.Adapter<RepositoryRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root), IRepositoryItemView {
        override fun setName(name: String) {
            binding.repositoryName.text = name
        }

        override var pos = INVALID_INDEX
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return presenter.bindView(holder.apply {
            pos = position
        })
    }
}