package com.deeromptech.androidmovieapptmdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.deeromptech.androidmovieapptmdb.presentation.genre.GenreScreen
import com.deeromptech.androidmovieapptmdb.presentation.movieList.MovieListScreen
import com.deeromptech.androidmovieapptmdb.presentation.movie_detail.MovieDetailPlaceholderScreen

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

        composable<MovieListRoute> {
            MovieListScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailRoute(
                            movieId = movieId
                        )
                    )
                }
            )
        }

        composable<MovieDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MovieDetailRoute>()

            MovieDetailPlaceholderScreen(
                movieId = route.movieId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}