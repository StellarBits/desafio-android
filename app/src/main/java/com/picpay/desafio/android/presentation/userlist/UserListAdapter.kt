package com.picpay.desafio.android.presentation.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.FragmentUserListItemBinding
import com.picpay.desafio.android.domain.model.User

class UserListAdapter : ListAdapter<User, UserListItemViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        return UserListItemViewHolder(
            FragmentUserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        val item = getItem(position) as User
        holder.bind(item)
    }

    override fun getItemCount(): Int = currentList.size
}