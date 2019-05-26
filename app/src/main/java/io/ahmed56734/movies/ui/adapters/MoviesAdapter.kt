package io.ahmed56734.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.databinding.MovieListItemBinding

@BindingMethods(
    value = [BindingMethod(
        type = AppCompatImageButton::class,
        attribute = "app:srcCompat",
        method = "setImageDrawable"
    )]
)
class MoviesAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var onMovieClicked: ((movie: Movie) -> Unit)? = null
    var onBookmarkClicked: ((movie: Movie) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            holder.bind(getItem(position)!!)
        }
    }


    inner class MovieViewHolder(itemView: View, private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onMovieClicked?.invoke(getItem(adapterPosition)!!)
            }

            binding.bookmarkIcon.setOnClickListener {
                onBookmarkClicked?.invoke(getItem(adapterPosition)!!)
            }
        }

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.positionTextView.text = "#${adapterPosition + 1}"
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }
}