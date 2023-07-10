package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentForksBinding
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.presenter.ForksPresenter
import com.example.githubclient.mvp.view.ForksView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.utils.GITHUB_USER
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ForksFragment : MvpAppCompatFragment(), ForksView, BackButtonListener {

    companion object {
        fun newInstance(repos: GithubUserRepositories) = ForksFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GITHUB_USER, repos)
            }
        }
    }

    private var _binding: FragmentForksBinding? = null
    private val binding get() = _binding!!

    private val presenter: ForksPresenter by moxyPresenter {
        ForksPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentForksBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<GithubUserRepositories>(GITHUB_USER)
            ?.let { presenter.show(it) }
    }

    override fun backPressed() = presenter.backPressed()

    override fun showNumberOfForks(forks: String) {
        binding.forksCount.text = forks
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}