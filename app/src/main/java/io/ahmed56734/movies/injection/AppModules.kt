package io.ahmed56734.movies.injection

import io.ahmed56734.movies.repository.popular.PopularMoviesRepository
import io.ahmed56734.movies.ui.popular.PopularMoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single {
        PopularMoviesRepository(localDataSource = get(), remoteDataSource = get())
    }


    viewModel {
        PopularMoviesViewModel(popularMoviesRepository = get())
    }

}