package io.ahmed56734.movies.injection

import io.ahmed56734.movies.data.repository.DetailsRepository
import io.ahmed56734.movies.data.repository.MoviesRepository
import io.ahmed56734.movies.data.repository.SearchRepository
import io.ahmed56734.movies.ui.details.MovieDetailsViewModel
import io.ahmed56734.movies.ui.favorites.FavoritesViewModel
import io.ahmed56734.movies.ui.popular.PopularMoviesViewModel
import io.ahmed56734.movies.ui.search.SearchViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single {
        MoviesRepository(localDataSource = get(), remoteDataSource = get())
    }

    single {
        DetailsRepository(remoteDataSource = get())
    }

    single {
        SearchRepository(localDataSource = get())
    }



    viewModel {
        PopularMoviesViewModel(moviesRepository = get())
    }

    viewModel {
        FavoritesViewModel(moviesRepository = get())
    }

    viewModel {
        MovieDetailsViewModel(detailsRepository = get())
    }



    viewModel {
        SearchViewModel(searchRepository = get())
    }

}