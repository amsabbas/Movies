package com.thiqah.movies.presentation.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.thiqah.movies.R
import com.thiqah.movies.data.source.remote.model.post.Movie
import kotlinx.android.synthetic.main.item_movie.view.*


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie>? = null

    val onMovieClickListener by lazy { MutableLiveData<Movie>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Movie) {
            itemView.tvMovieTitle.text = post.title

            itemView.setOnClickListener {
                onMovieClickListener.value = movies!![adapterPosition]
            }


        }

    }


}