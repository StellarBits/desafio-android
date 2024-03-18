package com.picpay.desafio.android.presentation.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.FragmentUserListBinding
import com.picpay.desafio.android.util.NO_DATA_IN_DATABASE_ERROR
import com.picpay.desafio.android.util.NO_ERROR
import com.picpay.desafio.android.util.SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserListViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.usersList.observe(viewLifecycleOwner) { users ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.usersSwipeRefresh.isRefreshing = false
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                userListAdapter.submitList(users)
            }
        }

        viewModel.getUsersListError.observe(viewLifecycleOwner) { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                val message =
                    if (response == NO_DATA_IN_DATABASE_ERROR)
                        getString(R.string.local_error)
                    else
                        getString(R.string.generic_error)

                binding.usersSwipeRefresh.isRefreshing = false
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE

                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            usersSwipeRefresh.setOnRefreshListener {
                getUsers()
            }
        }
    }

    private fun init() {
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar

        userListAdapter = UserListAdapter()

        recyclerView.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        if (viewModel.getUsersListError.value == NO_ERROR) {
            progressBar.visibility = View.VISIBLE
            getUsers()
        } else {
            userListAdapter.submitList(viewModel.usersList.value)
        }
    }

    private fun getUsers() {
        viewModel.getUsers()
    }
}