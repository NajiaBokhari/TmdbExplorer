package com.androidcodechallenge.tmdbexplorer.ui.movies.listing

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.models.Movie
import com.androidcodechallenge.tmdbexplorer.ui.base.BaseRecyclerAdapter
import com.androidcodechallenge.tmdbexplorer.utilities.ConstantUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*


class MovieListingAdapter(private var context: Context, private val listener: OnItemClickedListener?) :
    BaseRecyclerAdapter<Movie>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {

        if (holderItem is ItemViewHolder) {
            holderItem.bindItems(itemList[position], context, listener)
        }
    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(movie: Movie, context: Context, listener: OnItemClickedListener?) {
            itemView.item_movie_title.text = movie.title
            getDeviceYear(context, movie.release_date, itemView.item_movie_year)
            movie.poster_path?.let {
                Glide.with(context)
                    .load(ConstantUtils.getPosterPath(it))

                    .into(itemView.item_movie_banner)

            }

            itemView.setOnClickListener({
                listener?.openDetailView(movie)
            })
        }

        private fun getDeviceYear(context: Context, release_date: String, item_poster_year: TextView) {
            val currentDviceYear: Int = Calendar.getInstance().get(Calendar.YEAR)
            val releaseYear = release_date.split("-")
            item_poster_year.text = releaseYear[0]
            if (releaseYear[0].equals(currentDviceYear.toString())) {
                item_poster_year.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                item_poster_year.setTypeface(item_poster_year.getTypeface(), Typeface.BOLD);
            } else {
                item_poster_year.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                item_poster_year.setTypeface(item_poster_year.getTypeface(), Typeface.NORMAL);
            }
        }
    }


    interface OnItemClickedListener {
        fun openDetailView(Movie: Movie)
    }
}