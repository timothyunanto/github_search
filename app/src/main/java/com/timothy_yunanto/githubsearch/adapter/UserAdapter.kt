package com.timothy_yunanto.githubsearch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timothy_yunanto.githubsearch.service.model.response.User
import com.timothy_yunanto.githubsearch.R

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var dataset = emptyList<User>()
    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun setDataset(dataset: List<User>) {
        this.dataset = dataset
        notifyDataSetChanged()
    }

    fun getItem(position: Int): User {
        return this.dataset[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.card_user, parent, false))
    }

    override fun getItemCount(): Int {
        return this.dataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.dataset[position]

        holder.loginValue.text = item.login

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loginValue: TextView = itemView.findViewById(R.id.loginValue)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)

        init {
            this.itemView.setOnClickListener {
                listener?.onDataSetItemClick(itemView, dataset[adapterPosition], adapterPosition)
            }
        }
    }

    interface Listener {
        fun onDataSetItemClick(view: View, item: User, position: Int)
    }
}