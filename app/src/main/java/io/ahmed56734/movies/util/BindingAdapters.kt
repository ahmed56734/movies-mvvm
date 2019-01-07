package io.ahmed56734.movies.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide


@BindingAdapter("posterImage")
fun loadPoster(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("isRefreshing")
fun showSwipeRefreshLayoutLoading(swipeRefreshLayout: SwipeRefreshLayout, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> swipeRefreshLayout.isRefreshing = true
        else -> swipeRefreshLayout.isRefreshing = false
    }
}

//@BindingAdapter("srcCompat")
//fun bindSrcCompat(imageView: AppCompatImageView, drawable:Drawable){
//    imageView.setImageDrawable(drawable)
//}

