package io.ahmed56734.movies.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.ahmed56734.movies.R
import io.ahmed56734.movies.ui.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.android.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment() {
    private val TAG = FavoritesFragment::class.java.simpleName


    private val viewModel: FavoritesViewModel by viewModel()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.favoriteMoviesPagedList.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter.apply {
            onMovieClicked = {
                findNavController().navigate(
                    FavoritesFragmentDirections
                        .actionFavoritesFragmentToMovieDetailsFragment(it.id)
                )
            }
            onBookmarkClicked = {
                viewModel.toggleFavorites(it)
            }
        }

        favoriteMoviesRecyclerView.apply {
            adapter = moviesAdapter
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }//recyclerview setup
    }


}
