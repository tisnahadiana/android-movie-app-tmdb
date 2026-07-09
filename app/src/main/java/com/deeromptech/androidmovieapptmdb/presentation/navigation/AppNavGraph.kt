package com.deeromptech.androidmovieapptmdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.deeromptech.androidmovieapptmdb.presentation.genre.GenreScreen
import com.deeromptech.androidmovieapptmdb.presentation.movieList.MovieListPlaceholderScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GenreRoute
    ) {
        composable<GenreRoute> {
            GenreScreen(
                onGenreClick = { genre ->
                    navController.navigate(
                        MovieListRoute(
                            genreId = genre.id,
                            genreName = genre.name
                        )
                    )
                }
            )
        }

        composable<MovieListRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MovieListRoute>()

            MovieListPlaceholderScreen(
                genreId = route.genreId,
                genreName = route.genreName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}