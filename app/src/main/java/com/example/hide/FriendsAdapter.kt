package com.example.hide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendsAdapter(
    private val friends: Map<String, Boolean>,
    private val userRepository: UserRepository,
    ) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_myfriends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends.keys.elementAt(position)

        userRepository.getUserByUid(friend, { user ->
            holder.view.findViewById<TextView>(R.id.namerecommendedfriend3).text = user.nombre
            holder.view.findViewById<TextView>(R.id.userrecomended3).text = "@${user.usuario}"
        }, { error ->
            // Handle error here
        })

    }

    override fun getItemCount() = friends.size
}