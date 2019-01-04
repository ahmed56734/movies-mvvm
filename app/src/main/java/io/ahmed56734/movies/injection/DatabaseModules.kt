package io.ahmed56734.movies.injection

import io.ahmed56734.movies.data.local.LocalDataSource
import org.koin.dsl.module.module

val databaseModules = module {

    single {
        LocalDataSource()
    }
}