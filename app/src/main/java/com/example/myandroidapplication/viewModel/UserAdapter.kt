package com.example.myandroidapplication.viewModel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.model.User
import com.example.myandroidapplication.R
import com.example.myandroidapplication.view.ChatActivity

//Questa classe si occupa di gestire gli utenti nel database:
//come mostrarli, come ricercarli, tec...
class UserAdapter(val context: Context, /*Prima era val*/var userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]

        holder.textName.text = currentUser.name
        holder.textTag.text = currentUser.tag


        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)
        }

    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
        val textTag = itemView.findViewById<TextView>(R.id.txt_tag)
    }

    //Metodo aggiunto per SearchView
    fun setfiltereList(userList: ArrayList<User>){
        this.userList = userList
        notifyDataSetChanged()
    }

}