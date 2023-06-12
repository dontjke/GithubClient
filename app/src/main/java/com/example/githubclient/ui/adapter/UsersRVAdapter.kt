package com.example.githubclient.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemUserBinding
import com.example.githubclient.mvp.view.list.UserItemView

class UsersRVAdapter() : RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root), UserItemView {
        override var pos = -1
        override fun setLogin(text: String) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}