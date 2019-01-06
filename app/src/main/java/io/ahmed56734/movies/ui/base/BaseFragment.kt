//package io.ahmed56734.movies.ui.base
//
//
//import android.app.Activity
//import android.view.View
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import io.ahmed56734.movies.R
//
//
//abstract class BaseFragment : Fragment() {
//
//    fun showProgressBar(show: Boolean) {
//        if (context == null)
//            return
//        (context as Activity).findViewById<View>(R.id.mainProgressBar)?.apply {
//            visibility = if (show)
//                View.VISIBLE
//            else
//                View.INVISIBLE
//        }
//    }
//
//
//    fun showSwipeRefresh(show: Boolean) {
//        if (context == null)
//            return
//        (context as Activity)?.findViewById<SwipeRefreshLayout>(R.id.mainSwipeRefresh)?.run {
//            isRefreshing = show
//        }
//    }
//
//
//    fun showErrorView(show: Boolean, message: String? = null) {
//        if (context == null)
//            return
//        (context as Activity)?.findViewById<TextView>(R.id.errorView)?.run {
//            this.visibility = if (show) View.VISIBLE else View.INVISIBLE
//
//            if (message != null)
//                this.text = message
//            else
//                this.setText(R.string.default_error_message)
//        }
//
//    }
//
//
//}
