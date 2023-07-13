package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentFollowersBinding
import com.example.githubclient.di.follower.FollowerSubcomponent
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.presenter.FollowersPresenter
import com.example.githubclient.mvp.view.FollowersView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.FollowersRVAdapter
import com.example.githubclient.utils.GITHUB_USER
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FollowersFragment : MvpAppCompatFragment(), FollowersView, BackButtonListener {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private var followerSubcomponent: FollowerSubcomponent? = null
    private lateinit var adapter: FollowersRVAdapter

    companion object {
        fun newInstance(user: GithubUser) = FollowersFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GITHUB_USER, user)
            }
        }
    }

    private val presenter: FollowersPresenter by moxyPresenter {
        val user: GithubUser? = arguments?.getParcelable(GITHUB_USER)

        FollowersPresenter(user).apply {
            App.instance.initFollowerSubcomponent().inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFollowersBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun init() {
        binding.rvUserFollowers.layoutManager = LinearLayoutManager(context)
        adapter = FollowersRVAdapter(presenter.followersListPresenter).apply {
            followerSubcomponent?.inject(this)
        }
        adapter.imageLoader = presenter.imageLoader
        binding.rvUserFollowers.adapter = adapter
    }

    override fun updateList() {
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun backPressed() = presenter.backPressed()
}