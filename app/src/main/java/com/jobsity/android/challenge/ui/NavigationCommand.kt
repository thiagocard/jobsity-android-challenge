package com.jobsity.android.challenge.ui

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras

sealed class NavigationCommand {
    class To(val directions: NavDirections, val extras: NavModelExtras?): NavigationCommand()
    object Back: NavigationCommand()
    class BackTo(val destinationId: Int): NavigationCommand()
    object ToRoot: NavigationCommand()
}

class NavModelExtras(private vararg val pair: Pair<Int, String>) {

    fun toFragmentNavigatorExtras(view: View) = pair
        .map {
            Pair(view.findViewById<View>(it.first), it.second)
        }.toTypedArray()
        .run { FragmentNavigatorExtras(*this) }

}