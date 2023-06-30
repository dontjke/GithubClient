package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUsersBinding
import com.example.githubclient.mvp.model.api.ApiHolder
import com.example.githubclient.mvp.model.cache.RoomGithubUsersCacheImpl
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepositoryImpl
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.UsersRVAdapter
import com.example.githubclient.ui.image.GlideImageLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = UsersFragment()
    }


    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepositoryImpl(
                ApiHolder.api,
                App.networkStatus,
                RoomGithubUsersCacheImpl(Database.getInstance())
            ),
            App.instance.router,
            App.instance.androidScreens
        )
    }
    var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun init() {
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        binding.rvUsers.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}