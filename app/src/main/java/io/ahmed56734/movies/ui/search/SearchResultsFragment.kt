package io.ahmed56734.movies.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.ahmed56734.movies.R
import io.ahmed56734.movies.ui.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_search.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SearchResultsFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel.moviesPagedList.observe(this, Observer {
            moviesAdapter.submitList(it)
        })

        moviesAdapter.onMovieClicked = {
            val movieId = it.id
            val bundle = bundleOf("movieId" to movieId)
            with(findNavController()) {
                popBackStack()
                navigate(R.id.movieDetailsFragment, bundle)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        moviesRecyclerView.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


}
