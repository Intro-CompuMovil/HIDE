package com.example.hide

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val users: List<User>, private val onButtonClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView = view.findViewById(R.id.namerecommendedfriend)
        val userUsernameTextView: TextView = view.findViewById(R.id.userrecomended)
        val addButton: Button = view.findViewById(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_friends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.userNameTextView.text = user.nombre
        holder.userUsernameTextView.text = "@${user.usuario}"
        holder.addButton.setOnClickListener { onButtonClick(user) }

        holder.view.setOnClickListener {
            val context = holder.view.context
            val intent = Intent(context, FriendProfileActivity::class.java)
            intent.putExtra("USER_UID", user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = users.size
}