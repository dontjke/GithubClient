package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserBinding
import com.example.githubclient.mvp.model.entity.GithubUsersRepo
import com.example.githubclient.mvp.presenter.UserPresenter
import com.example.githubclient.mvp.view.UserView
import com.example.githubclient.ui.activity.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {
    companion object {
        fun newInstance(id: Int): UserFragment {
            val fragment = UserFragment()
            val arg = Bundle()
            arg.putInt("id", id)
            fragment.arguments = arg
            return fragment
        }
    }

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(GithubUsersRepo(), App.instance.router)
    }

    private var binding: FragmentUserBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false).also {
        binding = it
        binding?.userTextView?.text =
            presenter.usersRepo.getUsers()[arguments?.getInt("id") ?: 0].login
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun backPressed() = presenter.backPressed()

}