package com.moviessampleapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.load
import com.moviessampleapp.R
import com.moviessampleapp.data.MyMovie
import com.moviessampleapp.data.Search
import com.moviessampleapp.databinding.MovieItemBinding
import com.moviessampleapp.ui.MoviesSampleFragment
import javax.inject.Inject


class MoviesAdapter @Inject constructor(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(), Filterable {

    private val fullMovieList: MutableList<Search> = mutableListOf()
    private val adapterItems: MutableList<Search> = mutableListOf()

    fun setMovieList(movies: List<Search>) {
        fullMovieList.clear()
        fullMovieList.addAll(movies)
        setData(fullMovieList)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: List<Search>) {
        adapterItems.clear()
        adapterItems.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    override fun getItemCount(): Int = adapterItems.size

    override fun getItemId(position: Int): Long = adapterItems[position].imdbID.replace("tt","").toLong()

        //adapterItems[position].imdbID.replace("tt","").toLong()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return constraint?.toString()?.let { searchText ->
                    val filtered = fullMovieList.filter {
                        it.Title.contains(searchText, ignoreCase = true) ||
                                it.Type.contains(searchText, ignoreCase = true)
                    }
                    FilterResults().apply {
                        values = filtered
                        count = filtered.size
                    }
                } ?: FilterResults()
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                filterResults?.takeIf { it.count > 0 }?.let {
                    setData(it.values as List<Search>)
                }
            }
        }
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val context: Context = binding.root.context,
        private val resources: Resources = binding.root.resources
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Search) = movie.run {
            binding.titleYear.text = resources.getString(R.string.movie_title_and_year, Title, Year)
            binding.genre.text = Type
            binding.image.load(Poster, imageLoader) {
                placeholder(CircularProgressDrawable(context))
                error(R.drawable.ic_movie_placeholder)
            }
            binding.root.setOnClickListener {
                it.findFragment<MoviesSampleFragment>().onMovieItemClick(this)
            }
        }
    }
}