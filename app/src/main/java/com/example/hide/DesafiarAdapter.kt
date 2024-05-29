package com.example.hide

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DesafiarAdapter(
    private val friends: Map<String, Boolean>,
    private val userRepository: UserRepository,
    private val onChallenge: (String) -> Unit
) : RecyclerView.Adapter<DesafiarAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_desafio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendUid = friends.keys.elementAt(position)
        Log.i("mammamiaFriends", friendUid)

        userRepository.getUserByUid(friendUid, { user ->
            holder.view.findViewById<TextView>(R.id.challengefriend).text = user.nombre
            holder.view.findViewById<TextView>(R.id.userchallenge).text = "@${user.usuario}"

            holder.view.setOnClickListener {
                val context = holder.view.context
                val intent = Intent(context, FriendProfileActivity::class.java)
                intent.putExtra("USER_UID", friendUid)
                context.startActivity(intent)
            }

            holder.view.findViewById<Button>(R.id.buttonDesafiar).setOnClickListener {
                onChallenge(friendUid)
            }
        }, { error ->
            // Handle error here
        })
    }

    override fun getItemCount() = friends.size
}