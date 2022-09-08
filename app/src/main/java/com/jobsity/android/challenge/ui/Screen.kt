package com.jobsity.android.challenge.ui

/**
 * Available navigation routes.
 */
sealed class Screen(val route: String) {
    /** The main route with the list of all heroes. */
    object Shows : Screen("shows")
    object ShowDetail : Screen("show_detail")
    object Favorites : Screen("favorites")
    object Search : Screen("search")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}

object ScreenParams {
    const val SHOW_ID = "showId"
}