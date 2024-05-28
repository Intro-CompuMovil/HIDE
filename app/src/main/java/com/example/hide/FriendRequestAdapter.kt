package com.example.hide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendRequestAdapter(
    private val friendRequests: Map<String, String>,
    private val userRepository: UserRepository,
    private val onAccept: (String) -> Unit,
    private val onReject: (String) -> Unit
) : RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_requests, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendRequestUid = friendRequests.keys.elementAt(position)
        userRepository.getUserByUid(friendRequestUid, { user ->
            holder.view.findViewById<TextView>(R.id.namerecommendedfriend2).text = user.nombre
            holder.view.findViewById<TextView>(R.id.userrecomended2).text = "@${user.usuario}"

            holder.view.findViewById<ImageView>(R.id.imageViewAceptar).setOnClickListener {
                onAccept(friendRequestUid)
            }

            holder.view.findViewById<ImageView>(R.id.imageViewRechazar).setOnClickListener {
                onReject(friendRequestUid)
            }
        }, { error ->
            // Handle error here
        })
    }

    override fun getItemCount() = friendRequests.size
}