package com.example.basekotlinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlinapp.databinding.ItemPostBinding
import com.example.basekotlinapp.model.Post

class PostRecyclerAdapter : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {
    private var onPostClickListener: OnPostClickListener? = null
    private var postList = emptyList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            with(binding) {
                tvId.text = post.id
                tvName.text = post.name
                tvTitle.text = post.title

                root.setOnClickListener {
                    onPostClickListener?.onPostClick(post)
                }
            }
        }
    }

    fun setPosts(postList: List<Post>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    fun addPost(post: Post) {
        postList = postList + listOf(post)
        notifyItemInserted(postList.size)
    }

    fun removePost(post: Post) {
        val position = postList.indexOf(post)
        (postList as MutableList).remove(post)
        notifyItemRemoved(position)
    }

    fun setOnPostClickListener(listener: OnPostClickListener) {
        this.onPostClickListener = listener
    }

    interface OnPostClickListener {
        fun onPostClick(post: Post)
    }
}