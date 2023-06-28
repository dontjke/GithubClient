package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentRepositoryUserBinding
import com.example.githubclient.mvp.model.api.ApiHolder
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepositoryImpl
import com.example.githubclient.mvp.presenter.UserRepositoryPresenter
import com.example.githubclient.mvp.view.UserRepositoryView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.RepositoryRVAdapter
import com.example.githubclient.ui.image.GlideImageLoader
import com.example.githubclient.utils.GITHUB_USER
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserRepositoryFragment : MvpAppCompatFragment(), UserRepositoryView, BackButtonListener {

    private var _binding: FragmentRepositoryUserBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(user: GithubUser): UserRepositoryFragment {
            val fragment = UserRepositoryFragment()
            val arguments = Bundle()
            arguments.putParcelable(GITHUB_USER, user)
            fragment.arguments = arguments
            return fragment
        }
    }

    val presenter: UserRepositoryPresenter by moxyPresenter {
        val user: GithubUser? = arguments?.getParcelable(GITHUB_USER)
        UserRepositoryPresenter(
            user,
            App.instance.router,
            App.instance.androidScreens,
            RetrofitGithubUsersRepositoryImpl(ApiHolder.api),
            AndroidSchedulers.mainThread()
        )
    }

    var adapter: RepositoryRVAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepositoryUserBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun backPressed() = presenter.backPressed()
    override fun init(user: GithubUser) {
        user.avatarUrl?.let { GlideImageLoader().loadInto(it, binding.userAvatar) }
        binding.userLogin.text = user.login
        binding.rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = RepositoryRVAdapter(presenter.userRepositoryListPresenter)
        binding.rvRepos.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

}