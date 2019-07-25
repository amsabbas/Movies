package com.thiqah.posts.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiqah.posts.R
import com.thiqah.posts.data.source.remote.model.post.Post
import com.thiqah.posts.presentation.model.SingleLiveEvent
import kotlinx.android.synthetic.main.item_post.view.*


class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    var posts: ArrayList<Post>? = null

    val onPostClickListener by lazy { SingleLiveEvent<Post>() }
    val onPostEditClickListener by lazy { SingleLiveEvent<Post>() }
    val onPostDeleteClickListener by lazy { SingleLiveEvent<Post>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {
            itemView.tvPostTitle.text = post.title

            itemView.setOnClickListener {
                onPostClickListener.value = posts!![adapterPosition]
            }

            itemView.ibPostEdit.setOnClickListener {
                onPostEditClickListener.value = posts!![adapterPosition]
            }

            itemView.ibPostDelete.setOnClickListener {
                onPostDeleteClickListener.value = posts!![adapterPosition]
            }
        }

    }


    fun removePost(post: Post) {
        posts?.let {
            it.remove(post)
            notifyDataSetChanged()
        }
    }

    fun addPost(post: Post) {
        posts?.let {
            it.add(post)
            notifyDataSetChanged()
        }
    }

    //will be used for pagination
    fun addPost(posts: List<Post>) {
        this.posts?.let {
            it.addAll(posts)
            notifyDataSetChanged()
        }
    }
}