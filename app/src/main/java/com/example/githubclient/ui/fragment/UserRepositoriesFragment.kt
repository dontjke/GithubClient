package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentRepositoryUserBinding
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.presenter.UserRepositoriesPresenter
import com.example.githubclient.mvp.view.IImageLoader
import com.example.githubclient.mvp.view.UserRepositoryView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.RepositoriesRVAdapter
import com.example.githubclient.utils.GITHUB_USER
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class UserRepositoriesFragment : MvpAppCompatFragment(), UserRepositoryView, BackButtonListener {

    private var _binding: FragmentRepositoryUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    companion object {
        fun newInstance(user: GithubUser): UserRepositoriesFragment {
            val fragment = UserRepositoriesFragment()
            val arguments = Bundle()
            arguments.putParcelable(GITHUB_USER, user)
            fragment.arguments = arguments
            return fragment

        }
    }

    val presenter: UserRepositoriesPresenter by moxyPresenter {
        val user: GithubUser? = arguments?.getParcelable(GITHUB_USER)
        UserRepositoriesPresenter(user).apply {
            App.instance.appComponent.inject(this)
        }
    }

    var adapter: RepositoriesRVAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepositoryUserBinding.inflate(inflater, container, false).also {
        _binding = it
        App.instance.appComponent.inject(this)
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun backPressed() = presenter.backPressed()
    override fun init(user: GithubUser) {
        binding.rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = RepositoriesRVAdapter(presenter.userRepositoryListPresenter)
        binding.rvRepos.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun loadAvatarAndLogin(user: GithubUser) {
        user.avatarUrl?.let { imageLoader.loadInto(it, binding.userAvatar) }
        binding.userLogin.text = user.login
    }
}