package com.example.moviesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.CarouselItemBinding
import com.example.moviesapp.model.MovieItem
import com.example.moviesapp.model.Movies

class CarouselAdapter(private val movies: Movies) :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val movieTitle = itemView.findViewById<TextView>(R.id.tvCarouselTitle)
        private val movieCategory = itemView.findViewById<TextView>(R.id.tvCategory)

        fun bindCarousel(movie: MovieItem) {
            movieTitle.text = movie.title
            movieCategory.text = movie.genre_ids[0].toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCarousel(movies[position])

        holder.itemView.setOnClickListener {
            val rv = (holder.itemView.parent) as RecyclerView
            rv.smoothScrollToPosition(position)
        }
    }
}