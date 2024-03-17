package com.picpay.desafio.android.presentation.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.FragmentUserListBinding
import com.picpay.desafio.android.util.NO_DATA_IN_DATABASE_ERROR
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

        setObservers()
        init()
    }

    private fun setObservers() {
        viewModel.usersList.observe(viewLifecycleOwner) { response ->

            if (response.second == SUCCESS) {
                // Success
                progressBar.visibility = View.GONE

                userListAdapter.submitList(response.first)
            } else {
                // Failure
                val message =
                    if (response.second == NO_DATA_IN_DATABASE_ERROR)
                        getString(R.string.local_error)
                    else
                        getString(R.string.generic_error)

                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE

                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
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

        if (viewModel.usersList.value?.first.isNullOrEmpty()) {
            progressBar.visibility = View.VISIBLE
            viewModel.getUsers()
        } else {
            userListAdapter.submitList(viewModel.usersList.value?.first)
        }
    }
}