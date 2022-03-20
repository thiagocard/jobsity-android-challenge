package com.jobsity.android.challenge.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.jobsity.android.challenge.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

/**
 * A ViewModel that supports navigation commands.
 */
abstract class NavSupportViewModel : ViewModel() {

    val navigationCommands = MutableStateFlow<NavigationCommand?>(null)

    fun navigate(directions: NavDirections, extras: NavModelExtras? = null) {
        navigationCommands.value = NavigationCommand.To(directions, extras)
    }

    fun navigateBack() {
        navigationCommands.value = NavigationCommand.Back
    }

}

suspend fun NavSupportViewModel.collectNavCommand(
    view: View,
    navController: NavController
) {
    navigationCommands.collectLatest { cmd ->
        when (cmd) {
            NavigationCommand.Back -> TODO()
            is NavigationCommand.BackTo -> TODO()
            is NavigationCommand.To -> {
                cmd.extras?.toFragmentNavigatorExtras(view)?.let { extras ->
                    navController.navigate(cmd.directions, extras)
                } ?: navController.navigate(cmd.directions)
            }
            NavigationCommand.ToRoot -> TODO()
            null -> {}
        }
    }
}