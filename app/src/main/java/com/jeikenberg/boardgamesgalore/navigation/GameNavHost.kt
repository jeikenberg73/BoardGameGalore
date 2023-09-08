@file:Suppress("UNUSED_VARIABLE")

package com.jeikenberg.boardgamesgalore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jeikenberg.boardgamesgalore.ui.gamedetails.GameInfoScreen
import com.jeikenberg.boardgamesgalore.ui.gameselection.GameSelectionScreen
import com.jeikenberg.boardgamesgalore.ui.utilscreens.AddGameScreen
import com.jeikenberg.boardgamesgalore.viewmodels.GameSelectionViewModel

@Composable
fun GameNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = GameList.route,
        modifier = modifier
    ) {
        composable(route = GameList.route) {
            val viewModel: GameSelectionViewModel = hiltViewModel()

            // Need this to pull data from database to initialize the database.
            val gameUiState by viewModel.gameUiState.collectAsState()

            val searchedGames by viewModel.searchedGames.collectAsState()
            val searchText by viewModel.searchText.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()
            GameSelectionScreen(
                searchText = searchText,
                gameList = searchedGames,
                isSearching = isSearching,
                onValueChange = viewModel::onSearchTextChange,
                onAddGameClicked = {
                  navController.navigateSingleTopTo(AddGame.route)
                },
                modifier = modifier
            )
        }
        composable(route = AddGame.route) {
            AddGameScreen(modifier = modifier)
        }
        composable(
            route = GameInfo.routeWithArgs,
            arguments = GameInfo.arguments
        ) { navBackStackEntry ->
            GameInfoScreen(
                gameType = navBackStackEntry.arguments?.getLong(GameInfo.gameTypeArgs),
                onClickDescription = { gameId ->
                    navController.navigateToDetails(GameDescription.route, gameId)
                },
                onClickImages = { gameId ->
                    navController.navigateToDetails(GameImages.route, gameId)
                },
                modifier = modifier
            )
        }
        composable(
            route = GameInfo.routeWithArgs,
            arguments = GameInfo.arguments
        ) { navBackStackEntry ->
            val gameType =
                navBackStackEntry.arguments?.getLong(GameInfo.gameTypeArgs) ?: 0
            GameInfoScreen(
                gameType = gameType,
                onClickDescription = {},
                onClickImages = {},
                modifier = modifier
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToDetails(route: String, gameId: Long) {
    this.navigateSingleTopTo("${route}/${gameId}")
}