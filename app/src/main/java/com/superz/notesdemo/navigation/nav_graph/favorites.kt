package com.superz.notesdemo.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superz.notesdemo.feature.favorites.presentation.FavoritesScreen
import com.superz.notesdemo.navigation.Screen
import com.superz.notesdemo.navigation.Tab

fun NavGraphBuilder.favorites(navController: NavController) {
    navigation(
        startDestination = Screen.FavoritesScreen.route,
        route = Tab.Favorites.route
    ) {
        composable(
            route = Screen.FavoritesScreen.route
        ) {
            FavoritesScreen(
                navController = navController,
                onEditNoteClick = { noteId ->
                    navController.navigate(
                        route = "${Screen.AddEditNoteScreen.route}/$noteId"
                    )
                }
            )
        }
    }
}