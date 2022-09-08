package com.jobsity.android.challenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.ScreenParams
import com.jobsity.android.challenge.ui.common.AppScaffold
import com.jobsity.android.challenge.ui.favorite_shows.Favorites
import com.jobsity.android.challenge.ui.show_details.ShowDetail
import com.jobsity.android.challenge.ui.shows.Shows
import com.jobsity.android.challenge.ui.shows_search.Search
import com.jobsity.android.challenge.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)

            AppTheme {
                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                ) {
                    NavigationHost(navController)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    navigator: AppNavigator = AppNavigator()
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { route ->
            navController.navigate(route)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Shows.route
    ) {
        composable(Screen.Shows.route) {
            AppScaffold(navigator) { Shows(navigator) }
        }
        composable(Screen.Search.route) {
            AppScaffold(navigator) { Search(navigator) }
        }
        bottomSheet(
            route = "${Screen.ShowDetail.route}/{${ScreenParams.SHOW_ID}}",
            arguments = listOf(navArgument(ScreenParams.SHOW_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ShowDetail(
                id = backStackEntry.arguments?.getInt(ScreenParams.SHOW_ID) ?: -1
            )
        }
        composable(Screen.Favorites.route) {
            AppScaffold(navigator) { Favorites(navigator) }
        }
    }
}