package com.androidcodechallenge.tmdbexplorer.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.ui.movies.listing.MovieListingAdapter

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val ITEM_VIEW = 1
        const val ITEM_PROGRESS = 2
    }

    private var isLoading = false

    val itemList: MutableList<T> = ArrayList()


    fun setIsLoading(loading: Boolean) {
        isLoading = loading
        notifyDataSetChanged()
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun appendItems(items: List<T>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        itemList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (itemList.size - 1) && isLoading) {
            ITEM_PROGRESS
        } else {
            ITEM_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == ITEM_PROGRESS) {
            MovieListingAdapter.LoadMoreViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_more,
                    parent,
                    false
                )
            )

        } else {
            onCreateItemViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

}