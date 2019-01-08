package io.ahmed56734.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ahmed56734.movies.databinding.FragmentMovieDetailsBinding
import io.ahmed56734.movies.ui.base.BaseFragment
import io.ahmed56734.movies.util.Status
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment() {

    private lateinit var args: MovieDetailsFragmentArgs
    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var binding: FragmentMovieDetailsBinding
    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = MovieDetailsFragmentArgs.fromBundle(arguments!!)

        viewModel.loadMovieDetails(args.movieId)


        viewModel.movieDetailsLive.observe(this, Observer {
            if (it != null)
                binding.movieDetails = it
        })

        viewModel.movieCastLive.observe(this, Observer {
            castAdapter.submitList(it)
        })

        viewModel.networkStateLive.observe(this, Observer {
            binding.networkState = it
            binding.executePendingBindings()

            when (it?.status) {
                Status.RUNNING -> {
                    showErrorView(false)
                    mainContent.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    showErrorView(false)
                    mainContent.visibility = View.VISIBLE
                }
                Status.FAILED -> {
                    showErrorView(true)
                    mainContent.visibility = View.INVISIBLE
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        castRecyclerView.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        mainSwipeRefresh.setOnRefreshListener {
            viewModel.loadMovieDetails(args.movieId)
        }
    }

}
