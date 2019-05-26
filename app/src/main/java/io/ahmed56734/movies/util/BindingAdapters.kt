package io.ahmed56734.movies.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit


@BindingAdapter("posterImage")
fun loadPoster(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView)
        .load("https://image.tmdb.org/t/p/w200$url")
        .into(imageView)
}

@BindingAdapter("backdrop")
fun loadBackDrop(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView)
        .load("https://image.tmdb.org/t/p/w400$url")
//        .apply(RequestOptions().placeholder(R.drawable.backdrop_placeholder))
        .into(imageView)
}

@BindingAdapter("isRefreshing")
fun showSwipeRefreshLayoutLoading(swipeRefreshLayout: SwipeRefreshLayout, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> swipeRefreshLayout.isRefreshing = true
        else -> swipeRefreshLayout.isRefreshing = false
    }
}

@BindingAdapter("movieRuntime")
fun setFormattedDate(textView: TextView, minutes: Int) {
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    textView.text = "${hours}h ${minutes - TimeUnit.HOURS.toMinutes(hours)}min"
}

@BindingAdapter("isLoading")
fun showProgressBar(progressBar: ProgressBar, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> progressBar.visibility = View.VISIBLE
        else -> progressBar.visibility = View.INVISIBLE
    }
}

//@BindingAdapter("srcCompat")
//fun bindSrcCompat(imageView: AppCompatImageView, drawable:Drawable){
//    imageView.setImageDrawable(drawable)
//}

