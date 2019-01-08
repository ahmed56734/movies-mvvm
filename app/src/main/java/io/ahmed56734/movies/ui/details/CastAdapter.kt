package io.ahmed56734.movies.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.ahmed56734.movies.data.models.Cast
import io.ahmed56734.movies.databinding.CastListItemBinding

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = CastListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CastViewHolder(itemView: View, val binding: CastListItemBinding) : RecyclerView.ViewHolder(itemView) {

        fun bind(cast: Cast) {
            binding.actor = cast
            binding.executePendingBindings()
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }

        }
    }
}